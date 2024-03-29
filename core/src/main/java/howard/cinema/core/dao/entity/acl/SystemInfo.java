package howard.cinema.core.dao.entity.acl;

import howard.cinema.core.dao.entity.common.AbstractInsertTimeEntity;
import howard.cinema.core.dao.entity.common.StringUUIDEntity;

import java.time.LocalDateTime;

/**
 * @name: SystemInfo
 * @description: 系统日志表
 * @author: huojiajin
 * @time: 2020/5/27 10:48
 */
public class SystemInfo extends AbstractInsertTimeEntity {

    private String userId;//操作用户
    private String info;//操作详情
    private String eigenValue;//特征值

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getEigenValue() {
        return eigenValue;
    }

    public void setEigenValue(String eigenValue) {
        this.eigenValue = eigenValue;
    }
}
