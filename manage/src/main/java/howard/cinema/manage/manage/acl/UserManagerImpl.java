package howard.cinema.manage.manage.acl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import howard.cinema.core.dao.dict.acl.ErrorType;
import howard.cinema.core.dao.entity.acl.Cinema;
import howard.cinema.core.dao.entity.acl.Role;
import howard.cinema.core.dao.entity.acl.User;
import howard.cinema.core.dao.mapper.acl.CinemaMapper;
import howard.cinema.core.dao.mapper.acl.RoleMapper;
import howard.cinema.core.dao.mapper.acl.UserMapper;
import howard.cinema.core.manage.model.CommonResponse;
import howard.cinema.core.manage.tools.RegexValid;
import howard.cinema.core.manage.tools.SecurityUtil;
import howard.cinema.manage.manage.common.AbstractManager;
import howard.cinema.manage.model.acl.user.UserAddRequest;
import howard.cinema.manage.model.acl.user.UserEditRequest;
import howard.cinema.manage.model.acl.user.UserModel;
import howard.cinema.manage.model.common.CommonIdRequest;
import howard.cinema.manage.model.common.CommonPageRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @name: UserManagerImpl
 * @description: 用户ManagerImpl
 * @author: huojiajin
 * @time: 2020/5/27 17:20
 */
@Service
public class UserManagerImpl extends AbstractManager implements UserManager {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private CinemaMapper cinemaMapper;


    @Override
    public User findByLoginName(String loginName){
        return userMapper.findByLoginName(loginName);
    }

    @Override
    public String query(CommonPageRequest request){
        CommonResponse response = new CommonResponse();

        PageHelper.startPage(request.getPageNo(), request.getPageSize());
        List<User> userList = userMapper.findAll();
        PageInfo<User> page = new PageInfo<>(userList);
        //对结果进行转换
        page.setList(userList.stream().map(u ->{
            UserModel model = new UserModel();
            BeanUtils.copyProperties(u, model);
            Role role = roleMapper.findById(u.getRoleId());
            model.setRoleName(role.getName());
            Cinema cinema = cinemaMapper.findById(role.getCinemaId());
            model.setCinemaName(cinema.getName());
            return model;
        }).collect(Collectors.toList()));
        response.setData(page);
        return response.toJson();
    }

    @Override
    public String add(UserAddRequest addRequest) {
        CommonResponse response = new CommonResponse();
        String loginName = addRequest.getLoginName();
        if (!RegexValid.validLoginName(addRequest.getLoginName())){
            return response.setError(ErrorType.VALID, "用户名格式错误，仅允许数字和字母以及下划线");
        }
        if (!RegexValid.validMobile(addRequest.getMobile())){
            return response.setError(ErrorType.VALID, "手机号格式不正确");
        }
        User existsUser = findByLoginName(loginName);
        if (existsUser != null){
            response.setError(ErrorType.USEREXISTS);
        }
        Role role = roleMapper.findById(addRequest.getRoleId());
        if (role == null || role.isStop()){
            return response.setError(ErrorType.VALID, "角色不存在或已被删除");
        }
        User user = new User();
        BeanUtils.copyProperties(addRequest, user);
        //密码加密
        byte[] hashBytes = SecurityUtil.hash("123456".getBytes(), SecurityUtil.HashType.MD5);
        String password = new BigInteger(1, hashBytes).toString(16);
        user.setPassword(password);
        userMapper.persist(user);
        addSysLog("添加用户" + addRequest.getLoginName(), addRequest.getToken(), user.getId());
        response.setMessage("添加用户成功");
        return response.toJson();
    }

    @Override
    public String update(UserEditRequest editRequest){
        CommonResponse response = new CommonResponse();
        if (!RegexValid.validMobile(editRequest.getMobile())){
            return response.setError(ErrorType.VALID, "手机号格式不正确");
        }
        if (!hasText(editRequest.getName())){
            return response.setError(ErrorType.VALID, "请填写用户名");
        }
        User user = userMapper.findById(editRequest.getId());
        BeanUtils.copyProperties(editRequest, user);
        user.setUpdateTime(LocalDateTime.now());
        addSysLog("修改用户" + editRequest.getName(), editRequest.getToken(), user.getId());
        response.setMessage("修改用户成功");
        userMapper.update(user);
        return response.toJson();
    }

    @Override
    public String stop(CommonIdRequest deleteRequest){
        CommonResponse response = new CommonResponse();
        User user = userMapper.findById(deleteRequest.getId());
        if (user == null){
            return response.setError(ErrorType.NOUSER);
        }
        userMapper.updateStatus(User.UserStatus.INVALID, LocalDateTime.now(), deleteRequest.getId());
        addSysLog("停用用户" + user.getName(), deleteRequest.getToken(), deleteRequest.getId());
        response.setMessage("停用用户成功");
        return response.toJson();
    }

    @Override
    public String start(CommonIdRequest startRequest){
        CommonResponse response = new CommonResponse();
        User user = userMapper.findById(startRequest.getId());
        if (user == null){
            return response.setError(ErrorType.NOUSER);
        }
        userMapper.updateStatus(User.UserStatus.NORMAL, LocalDateTime.now(), startRequest.getId());
        addSysLog("启动用户" + user.getName(), startRequest.getToken(), startRequest.getId());
        response.setMessage("启用用户成功");
        return response.toJson();

    }
}
