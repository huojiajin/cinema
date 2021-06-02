package howard.cinema.manage.model.advice.schedule;

import howard.cinema.core.dao.dict.advice.MaterialType;
import howard.cinema.core.dao.dict.advice.PositionType;
import howard.cinema.manage.model.common.CommonRequest;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @name: AdviceScheduleAddRequest
 * @description: 广告排期添加Request
 * @author: huojiajin
 * @time: 2021/6/2 15:31
 */
public class AdviceScheduleAddRequest extends CommonRequest {

    @NotBlank(message = "请填写排期名称")
    private String name;//排期名称
    @NotNull(message = "请选择广告位")
    private PositionType positionType;//广告位
    @NotNull(message = "请选择素材类型")
    private MaterialType materialType;//广告位
    @NotBlank(message = "请填写上刊日期")
    private String startTime;//上刊日期
    @NotBlank(message = "请填写下刊日期")
    private String endTime;//下刊日期
    @NotNull(message = "请选择影城")
    private List<AdviceScheduleCinemaModel> cinemaList;//影城集合
    @NotNull(message = "请选择素材")
    private List<AdviceScheduleMaterialModel> materialList;//素材集合

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PositionType getPositionType() {
        return positionType;
    }

    public void setPositionType(PositionType positionType) {
        this.positionType = positionType;
    }

    public MaterialType getMaterialType() {
        return materialType;
    }

    public void setMaterialType(MaterialType materialType) {
        this.materialType = materialType;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public List<AdviceScheduleCinemaModel> getCinemaList() {
        return cinemaList;
    }

    public void setCinemaList(List<AdviceScheduleCinemaModel> cinemaList) {
        this.cinemaList = cinemaList;
    }

    public List<AdviceScheduleMaterialModel> getMaterialList() {
        return materialList;
    }

    public void setMaterialList(List<AdviceScheduleMaterialModel> materialList) {
        this.materialList = materialList;
    }
}
