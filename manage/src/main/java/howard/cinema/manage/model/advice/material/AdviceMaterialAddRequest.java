package howard.cinema.manage.model.advice.material;

import howard.cinema.core.dao.dict.advice.MaterialType;
import howard.cinema.manage.model.common.CommonRequest;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @name: AdviceMaterialAddRequest
 * @description: 广告素材添加Request
 * @author: huojiajin
 * @time: 2021/6/2 11:16
 */
public class AdviceMaterialAddRequest extends CommonRequest {

    @NotBlank(message = "请填写素材名称")
    private String name;//名称
    @NotNull(message = "请选择素材类型")
    private MaterialType materialType;//素材类型
    private String remark;//备注

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
