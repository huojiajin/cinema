package howard.cinema.core.dao.mapper.acl;

import howard.cinema.core.dao.entity.acl.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 *@ClassName UserMapper.java
 *@Description 用户表Mapper
 *@Author HuoJiaJin
 *@Date 2021/5/23 23:24
 *@Version 1.0
 **/
@Repository
public interface UserMapper {

    /**
     * @Name findAll
     * @Author HuoJiaJin
     * @Description 查询全部实体
     * @Date 2021/5/23 23:28
     * @Param []
     * @Return java.util.List<howard.cinema.core.dao.entity.acl.User>
     **/
    List<User> findAll();

    /**
     * @Name findById
     * @Author HuoJiaJin
     * @Description 查询单个实体
     * @Date 2021/5/23 23:29
     * @Param [id]
     * @Return howard.cinema.core.dao.entity.acl.User
     **/
    User findById(@Param("id")String id);

    /**
     * @Name persist
     * @Author HuoJiaJin
     * @Description 保存单个实体
     * @Date 2021/5/24 0:35
     * @Param [user]
     * @Return void
     **/
    void persist(User user);

    /**
     * @Name update
     * @Author HuoJiaJin
     * @Description 更新实体
     * @Date 2021/5/23 23:32
     * @Param [user]
     * @Return void
     **/
    void update(User user);

    /**
     * @Name updateStatus
     * @Author HuoJiaJin
     * @Description 更新用户状态
     * @Date 2021/5/23 23:36
     * @Param [status, id]
     * @Return void
     **/
    void updateStatus(@Param("param") User.UserStatus status, @Param("updateTime") LocalDateTime updateTime, @Param("id")String id);

    /**
     * @Name updatePassword
     * @Author HuoJiaJin
     * @Description 更新密码
     * @Date 2021/5/24 2:03
     * @Param [password, id]
     * @Return void
     **/
    void updatePassword(@Param("password")String password, @Param("updateTime") LocalDateTime updateTime, @Param("id")String id);

    /**
     * @Name listByRoleId
     * @Author HuoJiaJin
     * @Description 根据角色ID查询用户
     * @Date 2021/5/24 2:14
     * @Param [roleId]
     * @Return java.util.List<howard.cinema.core.dao.entity.acl.User>
     **/
    List<User> listByRoleId(@Param("roleId")String roleId);
    
    /**
     * @Name findByLoginName
     * @Author HuoJiaJin
     * @Description 根据登录名查找用户
     * @Date 2021/5/24 2:22
     * @Param [loginName]
     * @Return howard.cinema.core.dao.entity.acl.User
     **/
    User findByLoginName(@Param("loginName")String loginName);
}
