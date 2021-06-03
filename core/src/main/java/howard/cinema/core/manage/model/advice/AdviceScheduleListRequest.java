package howard.cinema.core.manage.model.advice;

import howard.cinema.core.dao.dict.advice.MaterialType;
import howard.cinema.core.dao.dict.advice.PositionType;

/**
 * @name: AdviceScheduleListRequest
 * @description: 广告排期查询Request
 * @author: huojiajin
 * @time: 2021/6/2 14:57
 */
public class AdviceScheduleListRequest{

    private String cinemaId;//影院Id
    private String materialId;//素材ID
    private PositionType positionType;//广告位
    private MaterialType materialType;//素材类型

    public String getCinemaId() {
        return cinemaId;
    }

    public void setCinemaId(String cinemaId) {
        this.cinemaId = cinemaId;
    }

    public String getMaterialId() {
        return materialId;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
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
}
