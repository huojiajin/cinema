package howard.cinema.core.dao.dict.advice;

/**
 * @name: MaterialStatus
 * @description: 素材状态
 * @author: huojiajin
 * @time: 2021/5/31 14:49
 */
public enum MaterialStatus {
    USING("使用中"){},
    UNUSE("未使用"){},
    ;

    MaterialStatus(String value) {
        this.value = value;
    }

    private String value;

    public String getValue() {
        return value;
    }
}
