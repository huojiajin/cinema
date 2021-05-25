package howard.cinema.manage.model.acl.user;

import howard.cinema.manage.model.common.CommonRequest;
import howard.cinema.manage.model.common.CommonTokenRequest;

import javax.validation.constraints.NotBlank;

/**
 * @name: UserPasswordEditRequest
 * @description: 修改密码Request
 * @author: huojiajin
 * @time: 2020/7/12 14:55
 */
public class UserPasswordEditRequest extends CommonTokenRequest {

    @NotBlank(message = "请填写旧密码")
    private String password;//密码
    @NotBlank(message = "请填写新密码")
    private String newPassword;//新密码

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
