package howard.cinema.manage.model.advice.material;

import howard.cinema.core.dao.entity.common.BaseEntity;

/**
 * @name: AdviceMaterialAddResponse
 * @description: 广告素材添加返回Response
 * @author: huojiajin
 * @time: 2021/6/2 11:22
 */
public class AdviceMaterialAddResponse extends BaseEntity {

    private String id;//

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
