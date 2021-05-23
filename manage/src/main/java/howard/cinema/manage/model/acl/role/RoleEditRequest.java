package howard.cinema.manage.model.acl.role;

import howard.cinema.manage.model.common.CommonRequest;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @name: RoleEditRequest
 * @description: 角色修改Request
 * @author: huojiajin
 * @time: 2020/5/28 11:41
 */
public class RoleEditRequest extends CommonRequest {

    @NotBlank(message = "请选择修改角色")
    private String id;//角色ID
    @NotBlank(message = "请填写登录名")
    private String name;// 角色名称
    @NotBlank(message = "请填写登录名")
    private String cinemaId;//影院Id
    private String info;// 描述
    @NotNull(message = "请填写排序")
    private Integer list;// 排序

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
}
