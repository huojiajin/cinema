package howard.cinema.core.dao.dict.advice;

/**
 * @name: MaterialType
 * @description: 素材类型
 * @author: huojiajin
 * @time: 2021/5/31 14:47
 */
public enum MaterialType {

    PICTURE("图片"){},
    VIDEO("视频"){},
    ;

    MaterialType(String value) {
        this.value = value;
    }

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
