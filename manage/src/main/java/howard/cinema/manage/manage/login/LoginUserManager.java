package howard.cinema.manage.manage.login;

import howard.cinema.manage.model.login.UserInfoEditRequest;
import howard.cinema.manage.model.acl.user.UserPasswordEditRequest;
import howard.cinema.manage.model.common.CommonTokenRequest;

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
    String loginout(CommonTokenRequest request);

    /**
     * @name: info
     * @description: 获取用户信息
     * @author: huojiajin
     * @para: [request]
     * @return: java.lang.String
    **/
    String info(CommonTokenRequest request);

    /**
     * @name: infoEdit
     * @description: 修改个人信息
     * @author: huojiajin
     * @para: [request]
     * @return: java.lang.String
    **/
    String infoEdit(UserInfoEditRequest request);

    /**
     * @name: getResourceList
     * @description: 获取用户资源列表
     * @author: huojiajin
     * @para: [request]
     * @return: java.lang.String
    **/
    String getResourceList(CommonTokenRequest request);
}
