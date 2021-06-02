package howard.cinema.core.dao.entity.advice;

import howard.cinema.core.dao.dict.advice.MaterialType;
import howard.cinema.core.dao.dict.advice.PositionType;
import howard.cinema.core.dao.entity.common.AbstractUpdateTimeEntity;

import java.time.LocalDate;

/**
 * @name: AdviceSchedule
 * @description: 广告排期实体
 * @author: huojiajin
 * @time: 2021/5/31 14:38
 */
public class AdviceSchedule extends AbstractUpdateTimeEntity {

    private String name;//排期名称
    private PositionType positionType;//广告位
    private MaterialType materialType;//素材类型
    private LocalDate startTime;//上刊日期
    private LocalDate endTime;//下刊日期
    private String operatorId;//操作人ID
    private String operatorName;//操作人员名称
    private boolean hasDelete;//是否删除

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PositionType getPositionType() {
        return positionType;
    }

    public void setPositionType(PositionType positionType) {
        this.positionType = positionType;
    }

    public MaterialType getMaterialType() {
        return materialType;
    }

    public void setMaterialType(MaterialType materialType) {
        this.materialType = materialType;
    }

    public LocalDate getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDate startTime) {
        this.startTime = startTime;
    }

    public LocalDate getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDate endTime) {
        this.endTime = endTime;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public boolean isHasDelete() {
        return hasDelete;
    }

    public void setHasDelete(boolean hasDelete) {
        this.hasDelete = hasDelete;
    }
}
