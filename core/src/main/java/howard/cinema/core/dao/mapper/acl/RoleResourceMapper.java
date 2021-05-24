package howard.cinema.core.dao.mapper.acl;

import howard.cinema.core.dao.entity.acl.RoleResource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *@ClassName RoleResource
 *@Description 角色资源Mapper
 *@Author HuoJiaJin
 *@Date 2021/5/23 22:11
 *@Version 1.0
 **/
@Component
public interface RoleResourceMapper {

    /**
     * @Name listByRoleId
     * @Author HuoJiaJin
     * @Description 根据角色Id查询权限
     * @Date 2021/5/23 22:13
     * @Param [roleId]
     * @Return java.util.List<howard.cinema.core.dao.entity.acl.RoleResource>
     **/
    List<RoleResource> listByRoleId(@Param("roleId")String roleId);

    /**
     * @Name persistAll
     * @Author HuoJiaJin
     * @Description 保存实体list
     * @Date 2021/5/23 22:29
     * @Param [resourceList]
     * @Return void
     **/
    void persistAll(List<RoleResource> resourceList);

    /**
     * @Name deleteByRoleId
     * @Author HuoJiaJin
     * @Description 根据角色Id删除权限
     * @Date 2021/5/23 22:32
     * @Param [roleId]
     * @Return void
     **/
    Integer deleteByRoleId(@Param("roleId")String roleId);
}
