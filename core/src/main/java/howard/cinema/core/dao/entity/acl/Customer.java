package howard.cinema.core.dao.entity.acl;

import howard.cinema.core.dao.entity.common.AbstractInsertTimeEntity;
import howard.cinema.core.dao.entity.common.AbstractUpdateTimeEntity;

/**
 * @ClassName: Customer
 * @Description: 客户实体
 * @Author HuoJiaJin
 * @Date 2021/5/24 0:52
 * @Version 1.0
 **/
public class Customer extends AbstractInsertTimeEntity {

    private String name;//客户名称

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
