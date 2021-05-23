package howard.cinema.manage.model.acl.role;

import howard.cinema.core.dao.entity.common.BaseEntity;

import java.util.List;

/**
 * @ClassName RoleResourceListResponse
 * @Description 角色可访问资源菜单查询
 * @Author HuoJiaJin
 * @Date 2020/6/21 1:27
 * @Version 1.0
 **/
public class RoleResourceListResponse extends BaseEntity {

    private List<Integer> resourceCodeList;

    public List<Integer> getResourceCodeList() {
        return resourceCodeList;
    }

    public void setResourceCodeList(List<Integer> resourceCodeList) {
        this.resourceCodeList = resourceCodeList;
    }
}
