package howard.cinema.core.dao.dict.acl;

/**
 * @ClassName: PosType
 * @Description: pos类型
 * @Author HuoJiaJin
 * @Date 2021/5/24 0:59
 * @Version 1.0
 **/
public enum PosType {
    DINGXIN("鼎鑫"),
    ;

    PosType(String value) {
        this.value = value;
    }

    private String value;

    public String getValue() {
        return value;
    }
}
