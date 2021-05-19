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
    A_XTGL_USER("用户管理", 11, 2, A_XTGL){},
    A_XTGL_ROLE("角色管理", 12, 2, A_XTGL){},
    A_XTGL_MOBILE("移动端权限管理", 13, 2, A_XTGL){},
    B_RYTJ("人员统计",2, 1, null){},
    B_RYTJ_CXRY("参训人员管理",21, 2, B_RYTJ){},
//    B_RYTJ_RLZB("人力指标分析",22, 2, B_RYTJ){},
    B_RYTJ_RYQK("人员情况统计",23, 2, B_RYTJ){},
    C_JBFCS("基本法测试", 3, 1, null){},
    C_JBFCS_SJGL("试卷管理", 31, 2, C_JBFCS){},
    C_JBFCS_ZLGL("资料管理", 32, 2, C_JBFCS){},
    C_JBFCS_JFGL("积分管理", 33, 2, C_JBFCS){},
    D_JGFX("架构分析",4, 1, null){},
//    E_XZZB("新资指标",5, 1, null){},
//    E_XZZB_ZBGL("指标管理",51, 2, E_XZZB){},
//    E_XZZB_CJRY("差距人员",52, 2, E_XZZB){},
    F_ZDYXX("自定义消息",6, 1, null){},
    G_DWFC("队伍分层",7, 1, null){},
//    G_DWFC_MDGZ("名单规则设置",71, 2, G_DWFC){},
//    G_DWFC_BMDGL("白名单管理",72, 2, G_DWFC){},
    G_DWFC_HMDGL("黑名单管理",72, 2, G_DWFC){},
    H_RYGL("荣誉管理",8, 1, null){},
    I_RLZLC("离职流程",9, 1, null){},
    I_RLZLC_RYZP("离职人员指派",91, 2, I_RLZLC){},
    I_RLZLC_LZBGL("离职表管理",92, 2, I_RLZLC){},
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
