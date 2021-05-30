package howard.cinema.manage.model.acl.cinema;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import howard.cinema.core.dao.dict.acl.CinemaType;
import howard.cinema.core.dao.dict.acl.PosType;
import howard.cinema.manage.model.common.CommonRequest;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @name: CinemaEditRequest
 * @description: 修改影城Request
 * @author: huojiajin
 * @time: 2021/5/24 14:53
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CinemaEditRequest extends CommonRequest {

    @NotBlank(message = "请选择要更新的影城")
    private String id;//影城ID
    @NotBlank(message = "请选择上级影城")
    private String parentId;//上级影城ID
    @NotBlank(message = "请填写影城名称")
    private String name;//影城名称
    @NotBlank(message = "请填写影城编码")
    private String code;//机构编码
    @NotNull(message = "请选择机构类型")
    private CinemaType type;//机构类型
    @NotNull(message = "请选择POS类别")
    private PosType posType;//POS类别
    @NotBlank(message = "请选择所属客户")
    private String customerId;//客户ID
    private String info;//描述
    @NotNull(message = "请填写排序")
    private Integer list;//排序

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public CinemaType getType() {
        return type;
    }

    public void setType(CinemaType type) {
        this.type = type;
    }

    public PosType getPosType() {
        return posType;
    }

    public void setPosType(PosType posType) {
        this.posType = posType;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Integer getList() {
        return list;
    }

    public void setList(Integer list) {
        this.list = list;
    }
}
