package howard.cinema.manage.manage.login;

import howard.cinema.core.dao.dict.acl.ErrorType;
import howard.cinema.core.dao.entity.acl.User;
import howard.cinema.core.dao.mapper.acl.UserMapper;
import howard.cinema.core.manage.model.CommonResponse;
import howard.cinema.core.manage.tools.JsonTools;
import howard.cinema.core.manage.tools.SecurityUtil;
import howard.cinema.manage.manage.common.AbstractManager;
import howard.cinema.manage.manage.tools.MyMecachedPrefix;
import howard.cinema.manage.model.acl.user.UserPasswordEditRequest;
import howard.cinema.manage.model.common.CommonRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigInteger;
import java.time.LocalDateTime;

/**
 * @name: LoginUserManagerImpl
 * @description: 登陆用户相关ManagerImpl
 * @author: huojiajin
 * @time: 2020/7/15 15:16
 */
@Service
public class LoginUserManagerImpl extends AbstractManager implements LoginUserManager {

    @Autowired
    private UserMapper userMapper;

    @Override
    public String passwordEdit(UserPasswordEditRequest request){
        CommonResponse response = new CommonResponse();
        if (request.getNewPassword().equals(request.getPassword())){
            return response.setError(ErrorType.VALID, "新密码与旧密码相同");
        }
        User user = getUser(request.getToken());
        if (user == null) return response.setError(ErrorType.NOLOGIN);
        //校验密码
        byte[] hashBytes = SecurityUtil.hash(request.getPassword().getBytes(), SecurityUtil.HashType.MD5);
        String requestPassword = new BigInteger(1, hashBytes).toString(16);
        if (!requestPassword.equals(user.getPassword())){
            return response.setError(ErrorType.PASSWORD);
        }

        //新密码加密
        byte[] newHashBytes = SecurityUtil.hash(request.getNewPassword().getBytes(), SecurityUtil.HashType.MD5);
        String password = new BigInteger(1, newHashBytes).toString(16);
        userMapper.updatePassword(password, LocalDateTime.now(),user.getId());

        addSysLog( user.getName() +"修改密码", request.getToken(), user.getId());

        response.setMessage("修改密码成功");
        return response.toJson();
    }

    @Override
    public String loginout(CommonRequest request){
        CommonResponse response = new CommonResponse();
        Object userObj = memcachedClient.get(MyMecachedPrefix.loginTokenPrefix + request.getToken());
        if (userObj != null) {
            try {
                User user = JsonTools.json2Object(String.valueOf(userObj), User.class);
                memcachedClient.delete(MyMecachedPrefix.loginResourcePrefix + user.getId());
                memcachedClient.delete(MyMecachedPrefix.loginTokenPrefix + request.getToken());
            } catch (IOException e) {
                logger.error("", e);
            }
        }
        response.setMessage("退出登录成功！");
        return response.toJson();
    }
}
