package howard.cinema.core.dao.dict.acl;

/**
 * @name: ErrorType
 * @description: 错误类型
 * @author: huojiajin
 * @time: 2020/5/27 15:33
 */
public enum ErrorType {

    //通用
    VALID(70000, "校验未通过"){},
    CONVERT(70001, "转换出错"){},
    //登录
    VERIFY(70002, "验证码不正确"){},
    LOGIN(70003, "用户名或密码错误"){},
    NOLOGIN(70004, "用户未登陆"){},
    PASSWORD(71000, "密码错误"){},
    //系统管理
    USEREXISTS(70005, "用户已存在"){},
    NORESOURCE(70006, "无该菜单权限"){},
    NOUSER(70007, "用户不存在"){},
    NOROLE(70008, "角色不存在"){},
    HASUSER(70009, "该角色下存在在用用户"){},

    ;

    private final int errCode;
    private final String errMsg;

    private ErrorType(int errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public int getErrCode() {
        return errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }
}
