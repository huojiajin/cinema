package howard.cinema.manage.manage.acl;

import howard.cinema.manage.model.acl.cinema.CinemaAddRequest;
import howard.cinema.manage.model.acl.cinema.CinemaEditRequest;
import howard.cinema.manage.model.acl.role.RoleAddRequest;
import howard.cinema.manage.model.acl.role.RoleEditRequest;
import howard.cinema.manage.model.acl.role.RoleResourceRequest;
import howard.cinema.manage.model.common.CommonIdRequest;
import howard.cinema.manage.model.common.CommonPageRequest;
import howard.cinema.manage.model.common.CommonRequest;

/**
 * @name: RoleManager
 * @description: 影城Manager
 * @time: 2020/5/27 10:55
 */
public interface CinemaManager {

    /**
     * @name: query
     * @description: 查询影城
     * @author: huojiajin
     * @para: [request]
     * @return: java.lang.String
    **/
    String query(CommonPageRequest request);

    /**
     * @name: list
     * @description: 查询影城列表
     * @author: huojiajin
     * @para: [request]
     * @return: java.lang.String
    **/
    String list(CommonRequest request);

    /**
     * @name: add
     * @description: 添加影城
     * @author: huojiajin
     * @para: [roleAddRequest]
     * @return: java.lang.String
    **/
    String add(CinemaAddRequest roleAddRequest);

    /**
     * @name: update
     * @description: 更新影城
     * @author: huojiajin
     * @para: [editRequest]
     * @return: java.lang.String
    **/
    String update(CinemaEditRequest editRequest);

    /**
     * @name: disable
     * @description: 停用影城
     * @author: huojiajin
     * @para: [deleteRequest]
     * @return: java.lang.String
    **/
    String disable(CommonIdRequest deleteRequest);

    /**
     * @name: enable
     * @description: 启用影城
     * @author: huojiajin
     * @para: [deleteRequest]
     * @return: java.lang.String
    **/
    String enable(CommonIdRequest deleteRequest);
}
