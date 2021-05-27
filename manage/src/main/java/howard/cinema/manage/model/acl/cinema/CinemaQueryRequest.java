package howard.cinema.manage.model.acl.cinema;

import howard.cinema.manage.model.common.CommonRequest;

/**
 * @name: CinemaQueryRequest
 * @description: 影城查询Request
 * @author: huojiajin
 * @time: 2021/5/27 14:56
 */
public class CinemaQueryRequest extends CommonRequest {

    private String parentId;//上级影城ID

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}
