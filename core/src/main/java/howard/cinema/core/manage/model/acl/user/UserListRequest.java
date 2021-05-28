package howard.cinema.core.manage.model.acl.user;

import howard.cinema.core.dao.entity.common.BaseEntity;

import java.util.List;

/**
 * @ClassName: UserListRequest
 * @Description: 查询用户列表Request
 * @Author HuoJiaJin
 * @Date 2021/5/27 23:52
 * @Version 1.0
 **/
public class UserListRequest extends BaseEntity {

    private String roleId;//角色ID
    private List<String> roleIds;//角色ID集合
    private String loginName;//登录名
    private String name;//用户名

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public List<String> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<String> roleIds) {
        this.roleIds = roleIds;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
