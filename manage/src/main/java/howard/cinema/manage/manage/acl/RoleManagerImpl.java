package howard.cinema.manage.manage.acl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import howard.cinema.core.dao.dict.acl.ErrorType;
import howard.cinema.core.dao.dict.acl.ResourceType;
import howard.cinema.core.dao.entity.acl.Cinema;
import howard.cinema.core.dao.entity.acl.Role;
import howard.cinema.core.dao.entity.acl.RoleResource;
import howard.cinema.core.dao.entity.acl.User;
import howard.cinema.core.dao.mapper.acl.CinemaMapper;
import howard.cinema.core.dao.mapper.acl.RoleMapper;
import howard.cinema.core.dao.mapper.acl.RoleResourceMapper;
import howard.cinema.core.dao.mapper.acl.UserMapper;
import howard.cinema.core.manage.model.CommonResponse;
import howard.cinema.manage.manage.common.AbstractManager;
import howard.cinema.manage.model.acl.role.*;
import howard.cinema.manage.model.common.CommonIdRequest;
import howard.cinema.manage.model.common.CommonListReponse;
import howard.cinema.manage.model.common.CommonPageRequest;
import howard.cinema.manage.model.common.CommonRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @name: RoleManagerImpl
 * @description: 角色相关ManagerImpl
 * @author: huojiajin
 * @time: 2020/5/28 10:48
 */
@Service
public class RoleManagerImpl extends AbstractManager implements RoleManager {

    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RoleResourceMapper roleResourceMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CinemaMapper cinemaMapper;

    @Override
    public String query(CommonPageRequest request){
        CommonResponse<PageInfo> response = new CommonResponse<>();
        PageHelper.startPage(request.getPageNo(), request.getPageSize());
        List<Role> roleList = roleMapper.findAll();
        PageInfo<Role> page = new PageInfo<>(roleList);
        //对结果进行转换
        page.setList(roleList.stream().map(r ->{
            RoleModel model = new RoleModel();
            BeanUtils.copyProperties(r, model);
            Cinema cinema = cinemaMapper.findById(r.getCinemaId());
            if (cinema != null) {
                model.setCinemaName(cinema.getName());
            }
            return model;
        }).collect(Collectors.toList()));
        response.setData(page);
        return response.toJson();
    }

    @Override
    public String list(CommonRequest request){
        CommonResponse<CommonListReponse> response = new CommonResponse<>();
        List<Role> list = roleMapper.findAll();
        CommonListReponse<Role> data = new CommonListReponse<>();
        data.setResult(list);
        response.setData(data);
        return response.toJson();
    }

    @Override
    public String add(RoleAddRequest addRequest){
        CommonResponse response = new CommonResponse();
        String name = addRequest.getName();
        if (!hasText(name)){
            return response.setError(ErrorType.VALID, "请填写角色名称");
        }
        Role oldRole = roleMapper.findByName(name);
        if (oldRole != null){
            return response.setError(ErrorType.VALID, "该角色已存在");
        }
        Cinema cinema = cinemaMapper.findById(addRequest.getCinemaId());
        if (cinema == null){
            return response.setError(ErrorType.VALID, "未找到对应影城");
        }
        RoleModel role = new RoleModel();
        BeanUtils.copyProperties(addRequest, role);
        role.setCinemaName(cinema.getName());
        roleMapper.persist(role);
        addSysLog("添加角色" + name, addRequest.getToken(), role.getId());
        response.setMessage("添加角色成功");
        return response.toJson();
    }

    @Override
    public String update(RoleEditRequest editRequest){
        CommonResponse response = new CommonResponse();
        Role role = roleMapper.findById(editRequest.getId());

        if (!hasText(editRequest.getName())){
            return response.setError(ErrorType.VALID, "请填写角色名称");
        }
        if (!role.getName().equals(editRequest.getName())){
            Role byName = roleMapper.findByName(editRequest.getName());
            if (byName != null) {
                return response.setError(ErrorType.VALID, "该角色已存在");
            }
        }
        Cinema cinema = cinemaMapper.findById(editRequest.getCinemaId());
        if (cinema == null){
            return response.setError(ErrorType.VALID, "未找到对应影城");
        }
        BeanUtils.copyProperties(editRequest, role);
        role.setUpdateTime(LocalDateTime.now());
        roleMapper.update(role);
        addSysLog("修改角色" + editRequest.getName(), editRequest.getToken(), editRequest.getId());
        response.setMessage("修改角色成功");
        return response.toJson();
    }

    @Override
    public String delete(CommonIdRequest deleteRequest){
        CommonResponse response = new CommonResponse();
        Role role = roleMapper.findById(deleteRequest.getId());
        if (role == null){
            return response.setError(ErrorType.NOROLE);
        }
        List<User> users = userMapper.listByRoleId(role.getId());
        if (!isEmpty(users)){
            List<User> normalUsers = users.stream().filter(u -> u.getStatus() == User.UserStatus.NORMAL).collect(Collectors.toList());
            if (!isEmpty(normalUsers)){
                return response.setError(ErrorType.HASUSER);
            }
        }
        roleMapper.updateDelete(deleteRequest.getId());
        addSysLog("删除角色" + role.getName(), deleteRequest.getToken(), deleteRequest.getId());
        response.setMessage("删除角色成功");
        return response.toJson();
    }

    @Override
    public String resourceConfig(RoleResourceRequest resourceRequest){
        CommonResponse response = new CommonResponse();
        List<RoleResource> roleResourceList = Lists.newArrayList();
        String roleId = resourceRequest.getId();
        Role role = roleMapper.findById(roleId);
        for (Integer code : resourceRequest.getResourceCodeList()) {
            RoleResource roleResource = new RoleResource();
            roleResource.setRoleId(roleId);
            try {
                roleResource.setResourceType(ResourceType.fromCode(code));
            } catch (InterruptedException e) {
                logger.error("", e);
                return response.setError(ErrorType.CONVERT);
            }
            roleResourceList.add(roleResource);
        }
        roleResourceUpdate(roleId, roleResourceList);
        addSysLog("配置角色" + role.getName() + "的权限", resourceRequest.getToken(), resourceRequest.getId());
        response.setMessage("配置权限成功");
        return response.toJson();
    }

    @Transactional(rollbackFor=Exception.class)
    public void roleResourceUpdate(String roleId, List<RoleResource> roleResourceList){
        roleResourceMapper.deleteByRoleId(roleId);
        roleResourceMapper.persistAll(roleResourceList);
    }

    @Override
    public String resourceList(CommonIdRequest request){
        CommonResponse<CommonListReponse> response = new CommonResponse<>();
        CommonListReponse<Integer> data = new CommonListReponse<>();
        List<RoleResource> roleResources = roleResourceMapper.listByRoleId(request.getId());
        if (!isEmpty(roleResources)){
            List<Integer> codeList = roleResources.stream().map(r -> r.getResourceType().getCode()).collect(Collectors.toList());
            data.setResult(codeList);
        }
        response.setData(data);
        return response.toJson();
    }
}
