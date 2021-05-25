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
public class CommonRequest extends CommonTokenRequest {

    @NotNull(message = "请填写资源编码")
    private int resourceCode;//菜单code

    public int getResourceCode() {
        return resourceCode;
    }

    public void setResourceCode(int resourceCode) {
        this.resourceCode = resourceCode;
    }
}
