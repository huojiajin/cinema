package howard.cinema.manage.manage.login;

import howard.cinema.manage.model.acl.user.UserPasswordEditRequest;
import howard.cinema.manage.model.common.CommonRequest;

/**
 *@ClassName LoginUserManager
 *@Description 登陆用户相关Manager
 *@Author HuoJiaJin
 *@Date 2020/7/15 15:16
 *@Version 1.0
 **/
public interface LoginUserManager {

    /**
     * @Name passwordEdit
     * @Author HuoJiaJin
     * @Description 修改密码
     * @Date 2020/7/15 15:17
     * @Param [request]
     * @return java.lang.String
     **/
    String passwordEdit(UserPasswordEditRequest request);

    /**
     * @Name loginOut
     * @Author HuoJiaJin
     * @Description 退出登录
     * @Date 2020/7/15 15:20
     * @Param [request]
     * @return java.lang.String
     **/
    String loginout(CommonRequest request);
}
