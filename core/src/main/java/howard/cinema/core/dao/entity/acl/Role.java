package howard.cinema.core.dao.entity.acl;

import howard.cinema.core.dao.entity.common.AbstractUpdateTimeEntity;

/**
 * @name: Role
 * @description: 角色表
 * @author: huojiajin
 * @time: 2020/5/25 15:38
 */
public class Role extends AbstractUpdateTimeEntity {

    private String name;// 角色名称
    private String cinemaId;//影城id
    private String info;// 描述
    private Integer list;// 排序
    private boolean hasDelete = false;//是否删除

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCinemaId() {
        return cinemaId;
    }

    public void setCinemaId(String cinemaId) {
        this.cinemaId = cinemaId;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Integer getList() {
        return list;
    }

    public void setList(Integer list) {
        this.list = list;
    }

    public boolean isHasDelete() {
        return hasDelete;
    }

    public void setHasDelete(boolean hasDelete) {
        this.hasDelete = hasDelete;
    }
}
