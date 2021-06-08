package howard.cinema.manage.manage.advice;

import howard.cinema.core.dao.dict.acl.ErrorType;
import howard.cinema.core.dao.dict.advice.FileUploadStatus;
import howard.cinema.core.dao.dict.advice.MaterialType;
import howard.cinema.core.dao.entity.acl.User;
import howard.cinema.core.dao.entity.advice.AdviceMaterial;
import howard.cinema.core.dao.mapper.advice.AdviceMaterialMapper;
import howard.cinema.core.manage.model.CommonResponse;
import howard.cinema.manage.manage.common.AbstractManager;
import howard.cinema.manage.manage.tools.FileConstants;
import howard.cinema.manage.model.advice.material.upload.AdviceMaterialCheckRequest;
import howard.cinema.manage.model.advice.material.upload.AdviceMaterialCheckResponse;
import howard.cinema.manage.model.advice.material.upload.AdviceMaterialUploadRequest;
import net.coobird.thumbnailator.Thumbnails;
import net.spy.memcached.MemcachedClient;
import org.apache.commons.codec.binary.Base64;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @name: AdviceMaterialUploadManagerImpl
 * @description: 广告素材上传ManagerImpl
 * @author: huojiajin
 * @time: 2021/6/1 9:39
 */
@Service
public class AdviceMaterialUploadManagerImpl extends AbstractManager implements AdviceMaterialUploadManager {

    @Autowired
    private MemcachedClient memcachedClient;
    @Autowired
    private AdviceMaterialMapper materialMapper;

    /**
     * 这个必须与前端设定的值一致
     */
    @Value("${upload.chunkSize}")
    private long chunkSize;

    //定义素材保存地址
    private static final String fileDirPath = System.getProperty("user.dir") + File.separator + "material" + File.separator;

    @Override
    public String checkFileMd5(AdviceMaterialCheckRequest request) {
        CommonResponse<AdviceMaterialCheckResponse> response = new CommonResponse<>();
        AdviceMaterialCheckResponse data = new AdviceMaterialCheckResponse();
        String id = request.getId();
        AdviceMaterial material = materialMapper.findById(id);
        if (material == null){
            return response.setError(ErrorType.VALID, "未找到对应的素材");
        }
        if (material.isUpload()){//已上传
            data.setStatus(FileUploadStatus.IS_HAVE);
            response.setData(data);
            return response.toJson();
        }
        Object processingObj = memcachedClient.get(FileConstants.MATERIAL_FILE_UPLOAD_STATUS + id);
        // 文件未上传过
        if (Objects.isNull(processingObj)) {
            data.setStatus(FileUploadStatus.NO_HAVE);
            response.setData(data);
            return response.toJson();
        }

        Object filePathOb = memcachedClient.get(FileConstants.MATERIAL_FILE_MD5_KEY + request.getMd5());
        String filePath = filePathOb.toString();

        //文件上传未完整
        List<Integer> missChunkList = new ArrayList<>();
        logger.info("filePath == > {}", filePath);
        try {
            byte[] completeList = Files.readAllBytes(Paths.get(filePath));

            for (int i = 0; i < completeList.length; i++) {
                if (completeList[i] != Byte.MAX_VALUE) {
                    missChunkList.add(i);
                }
            }
        } catch (IOException e) {
            logger.error("", e);
            return response.setError(ErrorType.CONVERT, e.getMessage());
        }
        logger.info("missChunkList===>{}", missChunkList.size());
        data.setStatus(FileUploadStatus.ING_HAVE);
        data.setMissChunkList(missChunkList);
        return response.setData(data);
    }


    @Override
    public String uploadFile(AdviceMaterialUploadRequest request) {
        User user = getUser(request.getToken());
        CommonResponse<String> response = new CommonResponse<>();
        AdviceMaterial material = materialMapper.findById(request.getId());
        if (material == null){
            return response.setError(ErrorType.VALID, "未找到对应的素材");
        }
        try {
            logger.info("上传分块文件开始,素材ID：{}，分块：{}", request.getId(), request.getChunk());
            //获取后缀
            String[] suffixArr = request.getName().split("\\.");
            String suffix = suffixArr[suffixArr.length - 1];
            String fileName = request.getId() + "." + suffix;
            String uploadDirPath = fileDirPath + request.getId();
            String tempFileName = fileName + FileConstants.FILE_NAME_TMP_SUFFIX;
            File tmpDir = new File(uploadDirPath);
            File tmpFile = new File(uploadDirPath, tempFileName);
            if (!tmpDir.exists()) {
                boolean mkdirs = tmpDir.mkdirs();
                if (!mkdirs) {
                    logger.error("目录创建失败");
                }
            }

            File confFile = new File(uploadDirPath, request.getId() + FileConstants.FILE_NAME_CONF_SUFFIX);
            addSysLog("上传分块文件,素材ID：" + request.getId() + "，分块：" + request.getChunk(), request.getToken(), request.getId());
            // 上传分块文件
            uploadChunkFileByRandomAccessFile(request, tmpFile);
            //标记上传进度
            tagChunkUpload(request, confFile);
            //检测是否全部上传完成
            boolean isComplete = isUploadComplete(confFile);
            if (isComplete) {
                //全部上传完成重命名文件
                String allFilePath = renameFile(tmpFile, fileName);
                material.setFilePath(allFilePath);
                material.setUpload(true);
                String thumbnailBase64 = "";
                if (material.getMaterialType() == MaterialType.PICTURE){
                    thumbnailBase64 = getImageThumbnail(allFilePath, suffix);
                }else if (material.getMaterialType() == MaterialType.VIDEO){
                    thumbnailBase64 = getVideoThumbnail(allFilePath);
                }
                material.setThumbnail(thumbnailBase64);
                material.setUpdateTime(LocalDateTime.now());
                materialMapper.update(material);
                addSysLog("上传素材文件成功", request.getToken(), request.getId());
            }
            // 更新上传结果
            updateUploadResult(request, isComplete, suffix);
            // 删除conf文件
            if (isComplete) {
                confFile.delete();
            }
        } catch (Exception e) {
            logger.info("上传分块文件失败,素材ID：{}，分块：{}", request.getId(), request.getChunk());
            logger.error("", e);
            return response.setError(ErrorType.CONVERT, e.getMessage());
        }
        logger.info("上传分块文件成功,素材ID：{}，分块：{}", request.getId(), request.getChunk());
        return response.setMessage("上传成功!");
    }

    /**
     * @name: getImageThumbnail
     * @description: 获取图片缩略图
     * @author: huojiajin
     * @para: [filePath]
     * @return: java.lang.String
    **/
    private String getImageThumbnail(String filePath, String suffix) throws IOException {

        try {
            String[] split = filePath.split("\\\\");
            // 使用源图像文件名创建ImageIcon对象。里面使用了toolkit，性能相对IOimage.read稳定，比thumbnailtor占用内存少
            ImageIcon imgIcon = new ImageIcon(split[split.length - 1]);
            // 得到Image对象。
            Image srcImage = imgIcon.getImage();
            // 计算比例
            int width = -1;
            int height = -1;
            int rate = 0;
            width = srcImage.getWidth(null); // 得到源图宽
            height = srcImage.getHeight(null); // 得到源图长
            if (width > 0 && height > 0) {
                int rate1 = width / 5;//宽度缩略比例
                int rate2 = height / 5;//高度缩略比例
                if (rate1 > rate2) {//宽度缩略比例大于高度缩略比例，使用宽度缩略比例
                    rate = rate1;
                } else {
                    rate = rate2;
                }
            } else {
                // 防止宽高未获取到，额，显然可能多此一举，图个安心
                rate = 1;
                width = 200;
                height = 200;
            }

            //计算缩略图最终的宽度和高度
            int newWidth = width / rate;
            int newHeight = height / rate;
            // 定义BufferedImage，后面那个TYPE_INT_RGB网上大多数用这个，所以没管为啥
            BufferedImage bufferedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
            // 创建Graphics2D对象，用于在BufferedImage对象上绘图。
            Graphics2D graphics = bufferedImage.createGraphics();
            // 设置图形上下文的当前颜色为白色。
            graphics.setColor(Color.WHITE);
            // 用图形上下文的当前颜色填充指定的矩形区域。
            graphics.fillRect(0, 0, newWidth, newHeight);
            // 按照缩放的大小在BufferedImage对象上绘制原始图像。
            graphics.drawImage(srcImage, 0, 0, newWidth, newHeight, null);
            // 释放图形上下文使用的系统资源。
            graphics.dispose();
            // 网上很多人说formatname为png生成出来的图片不会有错误，抱着侥幸心理，就写png了，貌似还有gif和jpg这两种可以用
            //将缩略图转换成base64
            ByteArrayOutputStream baos = new ByteArrayOutputStream();//io流
            ImageIO.write(bufferedImage, suffix, baos);//写入流中
            srcImage.flush();
            bufferedImage.flush();
            graphics.dispose();
            byte[] bytes = baos.toByteArray();//转换成字节
            String png_base64 = new String(Base64.encodeBase64(bytes));//转换成base64串
            png_base64 = png_base64.replaceAll("\n", "").replaceAll("\r", "");//删除 \r\n
            return "data:image/" + suffix + ";base64," + png_base64;
        } catch (Exception e) {
            logger.info(">>>>>>>>>>>>>>>>>>>>>>生成缩略图错误      " + e.getMessage());
            throw new IOException("生成缩略图错误");
        }
    }

    /**
     * 获取视频缩略图
     *
     * @param filePath：视频路径
     * @throws Exception
     */
    public String getVideoThumbnail(String filePath) throws Exception {
        String thumbnailBase64 = "";
        FFmpegFrameGrabber ff = FFmpegFrameGrabber.createDefault(filePath);
        ff.start();
        //判断是否是竖屏小视频
        String rotate = ff.getVideoMetadata("rotate");
        int ffLength = ff.getLengthInFrames();
        org.bytedeco.javacv.Frame f;
        int i = 0;
        int index = 3;//截取图片第几帧
        while (i < ffLength) {
            f = ff.grabImage();
            if (i == index) {
                if (null != rotate && rotate.length() > 1) {
                    thumbnailBase64 = doExecuteFrame(f, true);   //获取缩略图
                } else {
                    thumbnailBase64 = doExecuteFrame(f, false);   //获取缩略图
                }
                break;
            }
            i++;
        }
        ff.stop();
        return thumbnailBase64;  //返回的是视频第3帧的base64
    }

    /**
     * @name: doExecuteFrame
     * @description: 截取图片，返回base64
     * @author: huojiajin
     * @para: [f, bool]
     * @return: java.lang.String
    **/
    public String doExecuteFrame(org.bytedeco.javacv.Frame f, boolean bool) throws Exception {
        if (null == f || null == f.image) {
            return "";
        }
        Java2DFrameConverter converter = new Java2DFrameConverter();
        BufferedImage bi = converter.getBufferedImage(f);
        if (bool) {
            Image image = (Image) bi;
            bi = rotate(image, 90);//图片旋转90度
        }
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(bi, "png", os);
        InputStream input = new ByteArrayInputStream(os.toByteArray());
        return "data:image/png;base64," + inputStreamToBase64Str(input);
    }

    /**
     * 图片旋转角度
     *
     * @param src   源图片
     * @param angel 角度
     * @return 目标图片
     */
    public static BufferedImage rotate(Image src, int angel) {
        int src_width = src.getWidth(null);
        int src_height = src.getHeight(null);
        // calculate the new image size
        Rectangle rect_des = CalcRotatedSize(new Rectangle(new Dimension(
                src_width, src_height)), angel);
        BufferedImage res = null;
        res = new BufferedImage(rect_des.width, rect_des.height,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = res.createGraphics();
        // transform(这里先平移、再旋转比较方便处理；绘图时会采用这些变化，绘图默认从画布的左上顶点开始绘画，源图片的左上顶点与画布左上顶点对齐，然后开始绘画，修改坐标原点后，绘画对应的画布起始点改变，起到平移的效果；然后旋转图片即可)
        //平移（原理修改坐标系原点，绘图起点变了，起到了平移的效果，如果作用于旋转，则为旋转中心点）
        g2.translate((rect_des.width - src_width) / 2, (rect_des.height - src_height) / 2);
        //旋转（原理transalte(dx,dy)->rotate(radians)->transalte(-dx,-dy);修改坐标系原点后，旋转90度，然后再还原坐标系原点为(0,0),但是整个坐标系已经旋转了相应的度数 ）
        g2.rotate(Math.toRadians(angel), src_width / 2, src_height / 2);
        g2.drawImage(src, null, null);
        return res;
    }

    /**
     * 计算转换后目标矩形的宽高
     *
     * @param src   源矩形
     * @param angel 角度
     * @return 目标矩形
     */
    private static Rectangle CalcRotatedSize(Rectangle src, int angel) {
        double cos = Math.abs(Math.cos(Math.toRadians(angel)));
        double sin = Math.abs(Math.sin(Math.toRadians(angel)));
        int des_width = (int) (src.width * cos) + (int) (src.height * sin);
        int des_height = (int) (src.height * cos) + (int) (src.width * sin);
        return new java.awt.Rectangle(new Dimension(des_width, des_height));
    }



    private void uploadChunkFileByRandomAccessFile(AdviceMaterialUploadRequest request, File tmpFile) throws IOException {
        try (RandomAccessFile accessTmpFile = new RandomAccessFile(tmpFile, "rw")) {
            long offset = chunkSize * request.getChunk();
            // 定位到该分片的偏移量
            accessTmpFile.seek(offset);
            // 写入该分片数据
            accessTmpFile.write(request.getFile().getBytes());
        }
    }

    private void updateUploadResult(AdviceMaterialUploadRequest request, boolean isComplete, String suffix) {
        logger.info("isComplete ===>{}", isComplete);
        String id = request.getId();
        String md5 = request.getMd5();
        String fileMd5Key = FileConstants.MATERIAL_FILE_MD5_KEY + md5;
        if (isComplete) {
            memcachedClient.delete(FileConstants.MATERIAL_FILE_UPLOAD_STATUS + id);
            memcachedClient.delete(fileMd5Key);
        } else {
            Object statusOb = memcachedClient.get(FileConstants.MATERIAL_FILE_UPLOAD_STATUS + id);
            if (statusOb == null) {
                memcachedClient.add(FileConstants.MATERIAL_FILE_UPLOAD_STATUS + id, 0, false);
            }
            Object md5Ob = memcachedClient.get(fileMd5Key);
            if (md5Ob == null){
                memcachedClient.add(fileMd5Key, 0,
                        fileDirPath + id + File.separator + id + "." + suffix);
            }
        }
    }


    private void tagChunkUpload(AdviceMaterialUploadRequest request, File confFile) throws IOException {
        try (RandomAccessFile accessConfFile = new RandomAccessFile(confFile, "rw")) {
            // 把该分段标记为 true 表示完成
            accessConfFile.setLength(request.getChunks());
            accessConfFile.seek(request.getChunk() - 1);
            accessConfFile.write(Byte.MAX_VALUE);
        }
    }

    private boolean isUploadComplete(File confFile) throws IOException {
        // completeList 检查是否全部完成,如果数组里是否全部都是(全部分片都成功上传)
        byte[] completeList = Files.readAllBytes(confFile.toPath());
        for (byte chunkComplete : completeList) {
            if (chunkComplete != Byte.MAX_VALUE) {
                return false;
            }
        }
        return true;
    }

    /**
     * @name: renameFile
     * @description: 重命名文件，并返回路径
     * @author: huojiajin
     * @para: [toBeRenamed, toFileNewName]
     * @return: java.lang.String
    **/  
    private String renameFile(File toBeRenamed, String toFileNewName) {
        // 检查要重命名的文件是否存在，是否是文件
        if (!toBeRenamed.exists() || toBeRenamed.isDirectory()) {
            logger.info("File does not exist: {}", toBeRenamed.getName());
            return null;
        }
        File newFile = new File(toBeRenamed.getParent() + File.separatorChar + toFileNewName);
        // 修改文件名
        toBeRenamed.renameTo(newFile);
        return newFile.getAbsolutePath();
    }
}
