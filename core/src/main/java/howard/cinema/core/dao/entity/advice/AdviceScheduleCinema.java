package howard.cinema.core.dao.entity.advice;

import howard.cinema.core.dao.entity.common.AbstractInsertTimeEntity;

/**
 * @name: AdviceScheduleCinema
 * @description: 广告排期相关影城实体
 * @author: huojiajin
 * @time: 2021/6/2 14:33
 */
public class AdviceScheduleCinema extends AbstractInsertTimeEntity {

    private String scheduleId;//排期ID
    private String cinemaId;//影城ID
    private String cinemaName;//影城名称

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

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
