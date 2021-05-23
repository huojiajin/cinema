package howard.cinema.manage.model.common;

import howard.cinema.core.dao.entity.common.BaseEntity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @name: CommonRequest
 * @description: 公用Request(登陆除外)
 * @author: huojiajin
 * @time: 2020/5/28 11:39
 */
public class CommonRequest extends BaseEntity {

    @NotBlank(message = "请填写登陆凭证")
    private String token;//登陆凭证
    @NotNull(message = "请填写资源编码")
    private int resourceCode;//菜单code

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getResourceCode() {
        return resourceCode;
    }

    public void setResourceCode(int resourceCode) {
        this.resourceCode = resourceCode;
    }
}
