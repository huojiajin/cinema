package howard.cinema.manage.model.acl.role;

import howard.cinema.manage.model.common.CommonIdRequest;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @name: RoleResourceRequest
 * @description: 角色可访问资源配置Request
 * @author: huojiajin
 * @time: 2020/5/28 14:35
 */
public class RoleResourceRequest extends CommonIdRequest {

    @NotEmpty(message = "请选择可访问资源")
    private List<Integer> resourceCodeList;//配置资源集合

    public List<Integer> getResourceCodeList() {
        return resourceCodeList;
    }

    public void setResourceCodeList(List<Integer> resourceCodeList) {
        this.resourceCodeList = resourceCodeList;
    }
}
