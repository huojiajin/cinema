package howard.cinema.core.manage.model.advice;

import howard.cinema.core.dao.dict.advice.MaterialType;

/**
 * @name: AdviceMaterialListRequest
 * @description: 广告素材查询reqeust
 * @author: huojiajin
 * @time: 2021/5/31 15:21
 */
public class AdviceMaterialListRequest {

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
