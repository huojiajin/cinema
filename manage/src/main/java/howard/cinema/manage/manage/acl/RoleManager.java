package howard.cinema.manage.manage.acl;

import howard.cinema.manage.model.acl.role.RoleAddRequest;
import howard.cinema.manage.model.acl.role.RoleEditRequest;
import howard.cinema.manage.model.acl.role.RoleResourceRequest;
import howard.cinema.manage.model.common.CommonIdRequest;
import howard.cinema.manage.model.common.CommonPageRequest;
import howard.cinema.manage.model.common.CommonRequest;

/**
 * @name: RoleManager
 * @description: 角色Manager
 * @time: 2020/5/27 10:55
 */
public interface RoleManager {

    String query(CommonPageRequest request);

    String list(CommonRequest request);

    String add(RoleAddRequest roleAddRequest);

    String update(RoleEditRequest editRequest);

    String delete(CommonIdRequest deleteRequest);

    String resourceConfig(RoleResourceRequest resourceRequest);

    String resourceList(CommonIdRequest request);
}
