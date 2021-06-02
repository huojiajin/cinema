package howard.cinema.manage.model.advice.material.upload;

import howard.cinema.core.dao.dict.advice.FileUploadStatus;
import howard.cinema.core.dao.entity.common.BaseEntity;

import java.util.List;

/**
 * @name: AdviceMaterialCheckResponse
 * @description: 文件上传完整性校验Response
 * @author: huojiajin
 * @time: 2021/6/1 10:38
 */
public class AdviceMaterialCheckResponse extends BaseEntity {

    private FileUploadStatus status;//上传状态

    private List<Integer> missChunkList;//未上传部分

    public FileUploadStatus getStatus() {
        return status;
    }

    public void setStatus(FileUploadStatus status) {
        this.status = status;
    }

    public List<Integer> getMissChunkList() {
        return missChunkList;
    }

    public void setMissChunkList(List<Integer> missChunkList) {
        this.missChunkList = missChunkList;
    }
}
