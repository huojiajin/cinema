package howard.cinema.core.dao.dict.acl;

/**
 * @ClassName: CinemaType
 * @Description: 影院类型
 * @Author HuoJiaJin
 * @Date 2021/5/24 0:57
 * @Version 1.0
 **/
public enum CinemaType {
    LINE("院线"),
    CINEMA("影院"),
    ;


    CinemaType(String value) {
        this.value = value;
    }

    private String value;

    public String getValue() {
        return value;
    }
}
