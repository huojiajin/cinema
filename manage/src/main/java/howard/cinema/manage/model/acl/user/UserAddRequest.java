package howard.cinema.manage.model.acl.user;

import howard.cinema.manage.model.common.CommonRequest;

import javax.validation.constraints.NotBlank;

/**
 * @name: UserAddRequest
 * @description: 用户添加Request
 * @author: huojiajin
 * @time: 2020/5/28 15:01
 */
public class UserAddRequest extends CommonRequest {

    @NotBlank(message = "请填写登录名")
    private String loginName;//登录名
    @NotBlank(message = "请填写用户名")
    private String name;// 用户名
    @NotBlank(message = "请填写手机号")
    private String mobile;// 手机号
    @NotBlank(message = "请选择所属角色")
    private String roleId;// 用户所属角色Id

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
}
