package howard.cinema.manage.model.acl.cinema;

import howard.cinema.core.dao.entity.common.BaseEntity;

/**
 * @name: CinemaListModel
 * @description: 获取影城列表返回Model
 * @author: huojiajin
 * @time: 2021/5/24 14:49
 */
public class CinemaListModel extends BaseEntity {

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
