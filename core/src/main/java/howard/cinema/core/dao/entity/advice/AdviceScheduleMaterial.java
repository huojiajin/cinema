package howard.cinema.core.dao.entity.advice;

import howard.cinema.core.dao.entity.common.AbstractInsertTimeEntity;

/**
 * @name: AdviceScheduleMaterial
 * @description: 广告排期-相关素材实体
 * @author: huojiajin
 * @time: 2021/6/2 14:35
 */
public class AdviceScheduleMaterial extends AbstractInsertTimeEntity {

    private String scheduleId;//排期ID
    private String materialId;//素材ID
    private String materialName;//素材名称
    private String list;//素材排序

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getMaterialId() {
        return materialId;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getList() {
        return list;
    }

    public void setList(String list) {
        this.list = list;
    }
}
