package howard.cinema.manage.model.common;

import howard.cinema.core.dao.entity.common.BaseEntity;

import javax.validation.constraints.NotBlank;

/**
 * @name: CommonTokenRequest
 * @description: 通用请求参数-token
 * @author: huojiajin
 * @time: 2021/5/25 16:33
 */
public class CommonTokenRequest extends BaseEntity {

    @NotBlank(message = "请填写登陆凭证")
    private String token;//登陆凭证

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
