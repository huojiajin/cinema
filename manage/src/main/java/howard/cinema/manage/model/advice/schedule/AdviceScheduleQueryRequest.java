package howard.cinema.manage.model.advice.schedule;

import howard.cinema.core.dao.dict.advice.MaterialType;
import howard.cinema.core.dao.dict.advice.PositionType;
import howard.cinema.manage.model.common.CommonPageRequest;

/**
 * @name: AdviceScheduleQueryRequest
 * @description: 广告排期查询Request
 * @author: huojiajin
 * @time: 2021/6/2 14:57
 */
public class AdviceScheduleQueryRequest extends CommonPageRequest {

    private String cinemaId;//影院Id
    private PositionType positionType;//广告位
    private MaterialType materialType;//素材类型

    public String getCinemaId() {
        return cinemaId;
    }

    public void setCinemaId(String cinemaId) {
        this.cinemaId = cinemaId;
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
