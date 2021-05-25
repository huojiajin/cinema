package howard.cinema.manage.model.login;

import howard.cinema.core.dao.entity.common.BaseEntity;
import howard.cinema.manage.model.common.CommonRequest;
import howard.cinema.manage.model.common.CommonTokenRequest;

import javax.validation.constraints.NotBlank;

/**
 * @name: UserInfoEditRequst
 * @description: 用户信息 个人改动，仅能修改名称和手机号
 * @author: huojiajin
 * @time: 2021/5/25 16:30
 */
public class UserInfoEditRequest extends CommonTokenRequest {

    @NotBlank(message = "请填写用户名")
    private String name;//用户名
    @NotBlank(message = "请填写手机号")
    private String mobile;//手机号

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
}
