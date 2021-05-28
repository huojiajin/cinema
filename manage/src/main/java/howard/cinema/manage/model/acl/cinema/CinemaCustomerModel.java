package howard.cinema.manage.model.acl.cinema;

import howard.cinema.core.dao.entity.common.BaseEntity;

/**
 * @ClassName: CinemaCustomerModel
 * @Description: 客户model
 * @Author HuoJiaJin
 * @Date 2021/5/27 23:01
 * @Version 1.0
 **/
public class CinemaCustomerModel extends BaseEntity {

    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
