package howard.cinema.manage.model.advice.material;

import howard.cinema.core.dao.dict.advice.MaterialType;
import howard.cinema.manage.model.common.CommonPageRequest;
import howard.cinema.manage.model.common.CommonRequest;

/**
 * @name: AdviceMaterialQueryRequest
 * @description: 广告素材查询Request
 * @author: huojiajin
 * @time: 2021/6/2 11:00
 */
public class AdviceMaterialQueryRequest extends CommonPageRequest {

    private String name;//素材名称
    private MaterialType materialType;//素材类型

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MaterialType getMaterialType() {
        return materialType;
    }

    public void setMaterialType(MaterialType materialType) {
        this.materialType = materialType;
    }
}
