package howard.cinema.core.dao.dict.acl;

/**
 *@ClassName SectionType
 *@Description 所属部门类型枚举
 *@Author HuoJiaJin
 *@Date 2020/6/26 17:33
 *@Version 1.0
 **/
public enum SectionType {

    SECTION("部", "区域总", 0){},
    GROUP("组", "组经理", 1){},
    ;

    SectionType(String value, String name, int code) {
        this.value = value;
        this.name = name;
        this.code = code;
    }

    private String value;
    private String name;//领导名称
    private int code;

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public int getCode() {
        return code;
    }

    public static SectionType fromCode(int code) throws InterruptedException {
        for (SectionType type : SectionType.values()) {
            if (type.getCode() == code){
                return type;
            }
        }
        throw new InterruptedException("此类型不存在" + code);
    }
}
