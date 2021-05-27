package howard.cinema.core.dao.dict.acl;

import com.google.common.collect.Lists;

import java.util.List;

/**
 *@name: ResourceType
 *@description: 菜单枚举
 *@author: huojiajin
 *@time: 2020/5/26 15:21
**/
public enum ResourceType {
	
	A_XTGL("系统管理", 1, 1, null) {},
    A_XTGL_YCGL("影城管理", 11, 2, A_XTGL) {},
    A_XTGL_USER("用户管理", 12, 2, A_XTGL){},
    A_XTGL_ROLE("角色管理", 13, 2, A_XTGL){},

    B_ZFSZ("支付设置", 2, 1, null){},
    B_ZFSZ_ZFB("支付宝", 21, 2, B_ZFSZ){},
    B_ZFSZ_wx("微信", 22, 2, B_ZFSZ){},

    C_DDGL("订单管理", 3, 1, null){},
    C_DDGL_ZDDGL("总订单管理", 31, 2, C_DDGL){},
    C_DDGL_YPZDDGL("影票子订单管理", 32, 2, C_DDGL){},
    C_DDGL_MPZDDGL("卖品子订单管理", 33, 2, C_DDGL){},
    C_DDGL_ZFZDDGL("支付子订单管理", 34, 2, C_DDGL){},

    D_GGGL("广告管理", 4, 1, null){},
    D_GGGL_SCGL("素材管理", 41, 2, D_GGGL){},
    D_GGGL_PQGL("排期管理", 42, 2, D_GGGL){},
    D_GGGL_JBTJ("监播统计", 43, 2, D_GGGL){},

    E_SJGL("升级管理", 5, 1, null){},
    E_SJGL_APKGL("APK管理", 51, 2, E_SJGL){},
    E_SJGL_SJLB("升级列表", 52, 2, E_SJGL){},
    E_SJGL_SJLS("升级历史", 53, 2, E_SJGL){},

    F_MPGL("卖品管理", 6, 1, null){},

    G_SBGL("设备管理", 7, 1, null){},
    G_SBGL_ZDGL("终端管理", 71, 2, G_SBGL){},
    G_SBGL_CSHXX("初始化信息", 72, 2, G_SBGL){},
    G_SBGL_QPXQ("取票详情", 73, 2, G_SBGL){},
    ;

	private final String value;
	private final int code;
    private final int level;//从1开始，1为最高
    private final ResourceType type;//从属于哪个菜单

	private ResourceType(String value, int code, int level, ResourceType type) {
		this.value = value;
		this.code = code;
		this.level = level;
		this.type = type;
	}


	public String getValue() {
		return value;
	}

    public int getCode() {
        return code;
    }

    public int getLevel() {
        return level;
    }

    public ResourceType getType() {
        return type;
    }

    public static List<ResourceType> getLevelResourceType(int level){
        List<ResourceType> typeList = Lists.newArrayList();
        ResourceType[] typsArr = ResourceType.values();
        for (ResourceType type : typsArr) {
            if (type.getLevel() == level){
                typeList.add(type);
            }
        }
        return typeList;
    }

    public static ResourceType fromCode(int code) throws InterruptedException {
        ResourceType[] typsArr = ResourceType.values();
        for (ResourceType type : typsArr) {
            if (type.getCode() == code){
                return type;
            }
        }
        throw new InterruptedException("此类型不存在" + code);
    }
}
