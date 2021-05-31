package howard.cinema.core.dao.entity.advice;

import howard.cinema.core.dao.dict.advice.MaterialStatus;
import howard.cinema.core.dao.dict.advice.MaterialType;
import howard.cinema.core.dao.entity.common.AbstractUpdateTimeEntity;

/**
 * @name: AdviceMaterial
 * @description: 广告素材实体
 * @author: huojiajin
 * @time: 2021/5/31 14:39
 */
public class AdviceMaterial extends AbstractUpdateTimeEntity {

    private String name;//素材名称
    private MaterialType materialType;//素材类型
    private boolean upload;//是否已上传文件
    private String thumbnail;//缩略图
    private String filePath;//文件路径
    private String remark;//备注
    private MaterialStatus status;//状态
    private String operatorId;//上传人ID
    private boolean hasDelete;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MaterialType getMaterialType() {
        return materialType;
    }

    public void setMaterialType(MaterialType materialType) {
        this.materialType = materialType;
    }

    public boolean isUpload() {
        return upload;
    }

    public void setUpload(boolean upload) {
        this.upload = upload;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public MaterialStatus getStatus() {
        return status;
    }

    public void setStatus(MaterialStatus status) {
        this.status = status;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public boolean isHasDelete() {
        return hasDelete;
    }

    public void setHasDelete(boolean hasDelete) {
        this.hasDelete = hasDelete;
    }
}
