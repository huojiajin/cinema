package howard.cinema.manage.model.login;

import howard.cinema.core.dao.entity.common.BaseEntity;

import java.util.List;

/**
 * @name: LoginResponse
 * @description: 登陆返回Response
 * @author: huojiajin
 * @time: 2020/5/27 15:44
 */
public class LoginResponse extends BaseEntity {

    private String token;//用户token

    private String name;//姓名

    private String mobile;//手机号

    private String roleId;//所属角色Id

    private String roleName;//所属角色名称

    private String cinemaId;//所属影城ID

    private String cinemaName;//所属影城名称

    private List<ResourceModel> resourceList;//菜单权限

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
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

    public List<ResourceModel> getResourceList() {
        return resourceList;
    }

    public void setResourceList(List<ResourceModel> resourceList) {
        this.resourceList = resourceList;
    }
}
