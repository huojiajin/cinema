package howard.cinema.manage.manage.login;

import howard.cinema.core.dao.entity.acl.User;
import howard.cinema.manage.model.login.LoginRequest;
import howard.cinema.manage.model.login.LoginResponse;
import howard.cinema.manage.model.login.ResourceModel;

import java.util.List;

/**
 * @name: LoginManager
 * @description: 登陆Manager
 * @author: huojiajin
 * @time: 2020/5/27 15:51
 */
public interface LoginManager {

    String getVerifyImage();

    String login(LoginRequest loginRequest);

    LoginResponse assmbleLoginResponse(User user, String token, List<ResourceModel> resourceModelList);
}
