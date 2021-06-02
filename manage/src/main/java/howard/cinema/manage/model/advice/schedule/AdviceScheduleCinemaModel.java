package howard.cinema.manage.model.advice.schedule;

import howard.cinema.core.dao.entity.common.BaseEntity;

/**
 * @name: AdviceScheduleAddCinemaModel
 * @description: 广告排期添加-影城列表
 * @author: huojiajin
 * @time: 2021/6/2 15:32
 */
public class AdviceScheduleCinemaModel extends BaseEntity {

    private String cinemaId;//影城ID
    private String cinemaName;//影城名称

    public String getCinemaId() {
        return cinemaId;
    }

    public void setCinemaId(String cinemaId) {
        this.cinemaId = cinemaId;
    }

    public String getCinemaName() {
        return cinemaName;
    }

    public void setCinemaName(String cinemaName) {
        this.cinemaName = cinemaName;
    }
}
