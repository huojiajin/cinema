package howard.cinema.core.dao.entity.acl;

import howard.cinema.core.dao.dict.acl.CinemaType;
import howard.cinema.core.dao.dict.acl.PosType;
import howard.cinema.core.dao.entity.common.AbstractInsertTimeEntity;

/**
 * @ClassName: Cinema
 * @Description: 影城实体
 * @Author HuoJiaJin
 * @Date 2021/5/24 0:44
 * @Version 1.0
 **/
public class Cinema extends AbstractInsertTimeEntity {

    private String parentId;//上级影城ID
    private String name;//影城名称
    private String code;//机构编码
    private CinemaType type;//机构类型
    private PosType posType;//POS类别
    private String customerId;//客户ID
    private String info;//描述
    private int list;//排序
    private boolean stop = false;//是否停用

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

    public int getList() {
        return list;
    }

    public void setList(int list) {
        this.list = list;
    }

    public boolean isStop() {
        return stop;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }
}
