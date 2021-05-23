package howard.cinema.manage.model.login;

import howard.cinema.core.dao.entity.common.BaseEntity;

/**
 * @name: ResourceModel
 * @description: 返回资源菜单Model
 * @author: huojiajin
 * @time: 2020/5/28 10:22
 */
public class ResourceModel extends BaseEntity {

    private String resourceName;//资源名称
    private int resourceCode;//资源编码

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public int getResourceCode() {
        return resourceCode;
    }

    public void setResourceCode(int resourceCode) {
        this.resourceCode = resourceCode;
    }
}
