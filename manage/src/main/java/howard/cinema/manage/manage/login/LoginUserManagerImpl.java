package howard.cinema.manage.manage.login;

import howard.cinema.core.dao.dict.acl.ErrorType;
import howard.cinema.core.dao.entity.acl.RoleResource;
import howard.cinema.core.dao.entity.acl.User;
import howard.cinema.core.dao.mapper.acl.RoleResourceMapper;
import howard.cinema.core.dao.mapper.acl.UserMapper;
import howard.cinema.core.manage.model.CommonResponse;
import howard.cinema.core.manage.tools.JsonTools;
import howard.cinema.core.manage.tools.SecurityUtil;
import howard.cinema.manage.manage.common.AbstractManager;
import howard.cinema.manage.manage.tools.MyMecachedPrefix;
import howard.cinema.manage.model.login.LoginResponse;
import howard.cinema.manage.model.login.ResourceModel;
import howard.cinema.manage.model.login.UserInfoEditRequest;
import howard.cinema.manage.model.acl.user.UserPasswordEditRequest;
import howard.cinema.manage.model.common.CommonListReponse;
import howard.cinema.manage.model.common.CommonRequest;
import howard.cinema.manage.model.common.CommonTokenRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
    @Autowired
    private RoleResourceMapper roleResourceMapper;
    @Autowired
    private LoginManager loginManager;

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
    public String loginout(CommonTokenRequest request){
        CommonResponse response = new CommonResponse();
        Object userObj = memcachedClient.get(MyMecachedPrefix.loginTokenPrefix + request.getToken());
        if (userObj != null) {
            try {
                User user = JsonTools.json2Object(String.valueOf(userObj), User.class);
                addSysLog( user.getName() +"退出登录", request.getToken(), user.getId());
                memcachedClient.delete(MyMecachedPrefix.loginResourcePrefix + user.getId());
                memcachedClient.delete(MyMecachedPrefix.loginTokenPrefix + request.getToken());
            } catch (IOException e) {
                logger.error("", e);
            }
        }
        response.setMessage("退出登录成功！");
        return response.toJson();
    }

    @Override
    public String info(CommonTokenRequest request){
        CommonResponse<LoginResponse> response = new CommonResponse<>();
        User user = getUser(request.getToken());
        //查询菜单权限
        List<RoleResource> roleResources = roleResourceMapper.listByRoleId(user.getRoleId());
        List<ResourceModel> resourceModelList = roleResources.stream().map(this::getResourceModel).collect(Collectors.toList());
        LoginResponse loginResponse = loginManager.assmbleLoginResponse(user, request.getToken(), resourceModelList);
        response.setData(loginResponse);
        return response.toJson();
    }

    private ResourceModel getResourceModel(RoleResource r) {
        ResourceModel resourceModel = new ResourceModel();
        resourceModel.setResourceCode(r.getResourceType().getCode());
        resourceModel.setResourceName(r.getResourceType().getValue());
        return resourceModel;
    }

    @Override
    public String infoEdit(UserInfoEditRequest request){
        CommonResponse response = new CommonResponse();
        User userModel = getUser(request.getToken());
        User userEntity = userMapper.findById(userModel.getId());
        userEntity.setMobile(request.getMobile());
        userEntity.setName(request.getName());
        userMapper.update(userEntity);
        String userKey = MyMecachedPrefix.loginTokenPrefix + request.getToken();
        memcachedClient.add(userKey, 30 * 60, userEntity.toJson());
        addSysLog( userEntity.getName() +"修改个人信息", request.getToken(), userEntity.getId());
        response.setMessage("修改个人信息成功");
        return response.toJson();
    }

    @Override
    @SuppressWarnings("unchecked")
    public String getResourceList(CommonTokenRequest request){
        CommonResponse<CommonListReponse<Integer>> response = new CommonResponse<>();
        CommonListReponse<Integer> data = new CommonListReponse<>();
        User user = getUser(request.getToken());
        //更新memcached
        String resourceKey = MyMecachedPrefix.loginResourcePrefix + user.getId();
        Object resourceListObject = memcachedClient.get(resourceKey);
        List<Integer> resourceCodeList;
        if (resourceListObject == null) {
            resourceCodeList = getResourceCode(user);
        } else {
            resourceCodeList = (List<Integer>) resourceListObject;
            memcachedClient.add(resourceKey, 30*60, resourceCodeList);//保存到memacached
        }
        data.setResult(resourceCodeList);
        response.setData(data);
        return response.toJson();
    }

    public List<Integer> getResourceCode(User user) {
        List<RoleResource> roleResourceList = roleResourceMapper.listByRoleId(user.getId());
        return roleResourceList.stream().map(r -> r.getResourceType().getCode())
                .collect(Collectors.toList());
    }
}
