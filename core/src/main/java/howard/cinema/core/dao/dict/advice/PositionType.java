package howard.cinema.core.dao.dict.advice;

/**
 *@name: PositionType
 *@description: 广告位置类别
 *@author: huojiajin
 *@time: 2021/5/31 14:42
**/
public enum PositionType {
    FULL("全屏"){},
    TOP("顶部广告"){},
    BOTTOM("底部广告"){},
    FLOAT("浮动广告"){},
    PRINT("打印广告"){},
    DESKTOP_FULL("桌面全屏广告"){},
    DESKTOP_HALF("桌面左右屏广告"){},
    NAMING_TOP("冠名顶部"){},
    NAMING_BOTTOM("冠名底部"){},
    SEAT("座位图广告"){},
    ;

    PositionType(String value) {
        this.value = value;
    }

    private String value;

    public String getValue() {
        return value;
    }
}
