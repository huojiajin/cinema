package howard.cinema.core.manage.tools;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;
import org.apache.commons.codec.binary.Base64;
import org.springframework.util.StringUtils;

import java.io.*;
import java.util.Iterator;
import java.util.Map;

/**
 * @ClassName: PdfUtils
 * @Description: pdf生成工具类
 * @Author HuoJiaJin
 * @Date 2021/3/23 22:48
 * @Version 1.0
 **/
public class PdfUtils {

    /**
     * @Name fillPdf
     * @Author HuoJiaJin
     * @Description 写入pdf
     * @Date 2021/3/23 23:34
     * @Param [templatePath, newPDFPath, treeDto, imagePath]
     * @Return boolean
     **/
    public static File fillPdf(String templatePath, Map<String, String> treeDto, String newPDFPath, String newPdfName) throws IOException {
        try {
            File dir = new File(newPDFPath);
            if (!dir.exists()){
                dir.mkdirs();
            }
            File file = new File(newPDFPath + File.separator + newPdfName);
            if (!file.exists()){
                file.createNewFile();
            }
            FileOutputStream out = new FileOutputStream(file);
            InputStream fis = FileTools.getResourcesFileInputStream(templatePath);
            PdfReader reader = new PdfReader(fis);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            PdfStamper stamper = new PdfStamper(reader, bos);
            AcroFields form = stamper.getAcroFields();
            Iterator<String> it = form.getFields().keySet().iterator();
            while (it.hasNext()) {
                String key = it.next();
                String value = treeDto.get(key);
                if (!StringUtils.hasText(value)) continue;
                boolean isBase64 = value.contains("base64,");//判断是否为base64字符串
                if (isBase64) {//写入图片
                    //解析base64
                    String[] split = value.split(",");
                    byte[] decode = Base64.decodeBase64(split[1]);
                    //生成图片
                    Image image = Image.getInstance(decode);
                    form.addSubstitutionFont(BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED));
                    int pageNo = form.getFieldPositions(key).get(0).page;
                    Rectangle signRect = form.getFieldPositions(key).get(0).position;
                    float x = signRect.getLeft();
                    float y = signRect.getBottom();
                    PdfContentByte under = stamper.getOverContent(pageNo);
                    image.scaleToFit(signRect.getWidth(), signRect.getHeight());
                    image.setAbsolutePosition(x, y);
                    under.addImage(image);
                }else {//写入文字

                    // 默认12号字体
                    BaseFont baseFont = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
                    form.addSubstitutionFont(baseFont);
                    float fontSize = 12f;
                    PdfString da = form.getFieldItem(key).getMerged(0).getAsString(PdfName.DA);
                    if (da != null) {
                        Object dab[] = AcroFields.splitDAelements(da.toUnicodeString());
                        if (dab[AcroFields.DA_SIZE] != null)
                            fontSize = ((Float)dab[AcroFields.DA_SIZE]).floatValue();
                    }
                    // 文本框宽度
                    Rectangle position = form.getFieldPositions(key).get(0).position;
                    float textBoxWidth = position.getWidth();
                    // 文本框高度
                    float textBoxHeight = position.getHeight();
                    // 文本单行行高
                    float ascent = baseFont.getFontDescriptor(baseFont.ASCENT,fontSize);
                    // baseFont渲染后的文字宽度
                    float textWidth = baseFont.getWidthPoint(value, fontSize);

                    // 文本框高度只够写一行，并且文字宽度大于文本框宽度，则缩小字体
                    if (textBoxHeight < ascent * 1.6) {
                        while (textWidth > textBoxWidth) {
                            fontSize--;
                            textWidth = baseFont.getWidthPoint(value, fontSize);
                        }
                    }
                    form.setFieldProperty(key, "textsize", fontSize, null);
                    form.setField(key, value);
                }
            }
            stamper.setFormFlattening(true);
            stamper.close();
            Document doc = new Document();
            PdfCopy copy = new PdfCopy(doc, out);
            doc.open();
            PdfImportedPage importPage = copy.getImportedPage(new PdfReader(bos.toByteArray()), 1);
            copy.addPage(importPage);
            doc.close();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException(e.getMessage());
        }
    }
}
