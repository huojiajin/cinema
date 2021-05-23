package howard.cinema.manage.model.common;

import javax.validation.constraints.NotBlank;

/**
 * @name: CommonIdRequest
 * @description: 公用IDRequest
 * @author: huojiajin
 * @time: 2020/5/28 15:21
 */
public class CommonIdRequest extends CommonRequest {

    @NotBlank(message = "未选择更新对象")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
