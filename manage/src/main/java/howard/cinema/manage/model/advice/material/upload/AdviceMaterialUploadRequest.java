package howard.cinema.manage.model.advice.material.upload;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import howard.cinema.manage.manage.common.MultipartFileJsonHandler;
import howard.cinema.manage.model.common.CommonIdRequest;
import org.springframework.web.multipart.MultipartFile;

/**
 * @name: AdviceMaterialUploadRequest
 * @description: 广告素材上传Request
 * @author: huojiajin
 * @time: 2021/6/1 9:45
 */
public class AdviceMaterialUploadRequest extends CommonIdRequest {

    private String name;//文件名称
    private String md5;//MD5校验码
    private int chunks;//总分片数量
    private int chunk;//当前为第几块分片
    private long size;//当前分片大小
    private MultipartFile file;//分片文件

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public int getChunks() {
        return chunks;
    }

    public void setChunks(int chunks) {
        this.chunks = chunks;
    }

    public int getChunk() {
        return chunk;
    }

    public void setChunk(int chunk) {
        this.chunk = chunk;
    }

    public long getSize() {
        return size;
    }

    @JsonDeserialize(using = MultipartFileJsonHandler.class)
    public void setSize(long size) {
        this.size = size;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
