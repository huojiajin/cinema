package howard.cinema.manage.model.acl.cinema;

import howard.cinema.core.dao.dict.acl.CinemaType;
import howard.cinema.core.dao.dict.acl.PosType;
import howard.cinema.core.dao.entity.common.BaseEntity;

import java.util.List;

/**
 * @name: CinemaQueryResponse
 * @description: 查询影城返回Response
 * @author: huojiajin
 * @time: 2021/5/24 14:32
 */
public class CinemaQueryModel extends BaseEntity {

    private String id;//影城id
    private String name;//影城名称
    private String code;//机构编码
    private CinemaType type;//机构类型
    private PosType posType;//POS类别
    private String customerName;//客户
    private String info;//描述
    private int list;//排序
    private String parentId;//上级影院ID
    private boolean hasUse;//是否在用
    private List<CinemaQueryModel> result;//下级影院列表

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getList() {
        return list;
    }

    public void setList(int list) {
        this.list = list;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public boolean isHasUse() {
        return hasUse;
    }

    public void setHasUse(boolean hasUse) {
        this.hasUse = hasUse;
    }

    public List<CinemaQueryModel> getResult() {
        return result;
    }

    public void setResult(List<CinemaQueryModel> result) {
        this.result = result;
    }
}
