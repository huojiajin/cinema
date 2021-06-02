package howard.cinema.manage.manage.login;

import howard.cinema.core.dao.dict.acl.ErrorType;
import howard.cinema.core.dao.entity.acl.Cinema;
import howard.cinema.core.dao.entity.acl.Role;
import howard.cinema.core.dao.entity.acl.RoleResource;
import howard.cinema.core.dao.entity.acl.User;
import howard.cinema.core.dao.mapper.acl.CinemaMapper;
import howard.cinema.core.dao.mapper.acl.RoleMapper;
import howard.cinema.core.dao.mapper.acl.RoleResourceMapper;
import howard.cinema.core.manage.common.CommonAbstract;
import howard.cinema.core.manage.model.CommonResponse;
import howard.cinema.core.manage.tools.SecurityUtil;
import howard.cinema.manage.manage.acl.UserManager;
import howard.cinema.manage.manage.tools.MyMecachedPrefix;
import howard.cinema.manage.manage.tools.VerifyImage;
import howard.cinema.manage.model.login.LoginRequest;
import howard.cinema.manage.model.login.LoginResponse;
import howard.cinema.manage.model.login.ResourceModel;
import howard.cinema.manage.model.login.VerifyResponse;
import net.spy.memcached.MemcachedClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @name: LoginManagerImpl
 * @description: 登陆ManagerImpl
 * @author: huojiajin
 * @time: 2020/5/27 15:52
 */
@Service
public class LoginManagerImpl extends CommonAbstract implements LoginManager {

    @Autowired
    private MemcachedClient memcachedClient;
    @Autowired
    private UserManager userManager;
    @Autowired
    private RoleResourceMapper roleResourceMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private CinemaMapper cinemaMapper;

    @Override
    public String getVerifyImage() {

        CommonResponse<VerifyResponse> response = new CommonResponse<>();

        VerifyImage verifyImage = new VerifyImage();
        verifyImage.generate();
        String verifyId = UUID.randomUUID().toString().replace("-", "");
        //存到memcached
        memcachedClient.set(MyMecachedPrefix.loginVerifyImagePrefix + verifyId, 5 * 60, verifyImage.getVerifyCode());
        try {
            VerifyResponse verifyResponse = new VerifyResponse(verifyImage.getBuffImg(), verifyId);
            return response.setData(verifyResponse);
        } catch (IOException e) {
            logger.error("", e);
            return response.setError(ErrorType.CONVERT);
        }
    }

    @Override
    public String login(LoginRequest loginRequest) {

        CommonResponse<LoginResponse> response = new CommonResponse<>();
        String verifyKey = MyMecachedPrefix.loginVerifyImagePrefix + loginRequest.getVerifyId();

        //校验验证码
        String verifyCode = (String) memcachedClient.get(verifyKey);
        if (hasText(verifyCode)) {
            if (!toUpper(loginRequest.getVerifyCode()).equals(toUpper(verifyCode))) {
                return response.setError(ErrorType.VERIFY);
            }
        } else {
            response.setSuccess(false);
            response.setErrCode(ErrorType.VERIFY.getErrCode());
            response.setMessage("验证码已超时");
            return response.toJson();
        }
        //删除验证码缓存
        memcachedClient.delete(verifyKey);

        //校验用户名
        User user = userManager.findByLoginName(loginRequest.getLoginName());
        if (user == null) {
            return response.setError(ErrorType.LOGIN);
        } else if (user.getStatus() == User.UserStatus.INVALID) {
            response.setSuccess(false);
            response.setErrCode(ErrorType.LOGIN.getErrCode());
            response.setMessage("用户已停用");
            return response.toJson();
        }
        //校验密码
        byte[] hashBytes = SecurityUtil.hash(loginRequest.getPassword().getBytes(), SecurityUtil.HashType.MD5);
        String requestPassword = new BigInteger(1, hashBytes).toString(16);
        if (!requestPassword.equals(user.getPassword())) {
            return response.setError(ErrorType.LOGIN);
        }
        //存放登陆信息
        String token = UUID.randomUUID().toString().replace("-", "");
        memcachedClient.set(MyMecachedPrefix.loginTokenPrefix + token, 30 * 60, user.toJson());

        //查询菜单权限
        List<RoleResource> roleResources = roleResourceMapper.listByRoleId(user.getRoleId());
        List<ResourceModel> resourceModelList = roleResources.stream().map(this::getResourceModel).collect(Collectors.toList());

        //存放菜单权限至memcached
        List<Integer> resourceCodeList = roleResources.stream().map(r -> r.getResourceType().getCode()).collect(Collectors.toList());
        memcachedClient.set(MyMecachedPrefix.loginResourcePrefix + user.getId(), 30 * 60, resourceCodeList);

        //拼装返回信息
        LoginResponse loginResponse = assmbleLoginResponse(user, token, resourceModelList);
        return response.setData(loginResponse);
    }

    @Override
    public LoginResponse assmbleLoginResponse(User user, String token, List<ResourceModel> resourceModelList) {
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(token);
        Role role = roleMapper.findById(user.getRoleId());
        loginResponse.setRoleId(role.getId());
        loginResponse.setRoleName(role.getName());
        loginResponse.setCinemaId(role.getCinemaId());
        Cinema cinema = cinemaMapper.findById(role.getCinemaId());
        loginResponse.setCinemaName(cinema.getName());
        loginResponse.setName(user.getName());
        loginResponse.setMobile(user.getMobile());
        loginResponse.setResourceList(resourceModelList);
        return loginResponse;
    }

    private ResourceModel getResourceModel(RoleResource r) {
        ResourceModel resourceModel = new ResourceModel();
        resourceModel.setResourceCode(r.getResourceType().getCode());
        resourceModel.setResourceName(r.getResourceType().getValue());
        return resourceModel;
    }

    private String toUpper(String str) {
        char[] c = str.toCharArray();// 把字符串转化为字符数组

        for (int i = 0; i < c.length; i++) {// 循环字符数组

            if (Character.isUpperCase(c[i])) {// 判断是否是大写，如果是大写的就转化为小写。
                // 把c[i]转化为int型，加32后在转化为char型，重新赋值给c[i];
                c[i] = (char) ((int) c[i] + 32);
            } else if (Character.isDigit(c[i])) {// 判断是否是数字
                c[i] = c[i];
            }
        }
        return new String(c);
    }
}
