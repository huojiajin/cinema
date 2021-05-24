package howard.cinema.core.dao.entity.acl;

import howard.cinema.core.dao.dict.acl.ResourceType;
import howard.cinema.core.dao.entity.common.AbstractInsertTimeEntity;
import howard.cinema.core.dao.entity.common.AbstractUpdateTimeEntity;
import howard.cinema.core.dao.entity.common.StringUUIDEntity;

/**
 * @name: RoleResource
 * @description: 角色资源表
 * @author: huojiajin
 * @time: 2020/5/25 17:08
 */
public class RoleResource extends AbstractInsertTimeEntity {

    private String roleId;//角色ID
    private ResourceType resourceType;//菜单枚举

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }
}
