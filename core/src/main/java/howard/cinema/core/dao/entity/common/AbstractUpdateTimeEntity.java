package howard.cinema.core.dao.entity.common;

import java.time.LocalDateTime;

/**
 * @name: AbstractInsertTimeEntity
 * @description: 公用时间Entity
 * @author: huojiajin
 * @time: 2020/5/28 15:17
 */
public class AbstractUpdateTimeEntity extends AbstractInsertTimeEntity {

    private LocalDateTime updateTime;//更新时间

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
}
