package howard.cinema.core.dao.mapper.acl;

import howard.cinema.core.dao.entity.acl.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: RoleMapper
 * @Description: 角色Mapper
 * @Author HuoJiaJin
 * @Date 2021/5/23 3:09
 * @Version 1.0
 **/
@Component
public interface RoleMapper {

    /**
     * @Name findById
     * @Author HuoJiaJin
     * @Description 根据Id查询
     * @Date 2021/5/23 22:34
     * @Param [id]
     * @Return howard.cinema.core.dao.entity.acl.Role
     **/
    Role findById(@Param("id") String id);

    /**
     * @Name findAll
     * @Author HuoJiaJin
     * @Description 查询全部实体
     * @Date 2021/5/23 23:03
     * @Param []
     * @Return java.util.List<howard.cinema.core.dao.entity.acl.Role>
     **/
    List<Role> findAll();

    /**
     * @Name persist
     * @Author HuoJiaJin
     * @Description 保存单个实体
     * @Date 2021/5/23 22:35
     * @Param [role]
     * @Return void
     **/
    void persist(Role role);

    /**
     * @Name persistAll
     * @Author HuoJiaJin
     * @Description 保存多个实体
     * @Date 2021/5/23 22:35
     * @Param [roleList]
     * @Return void
     **/
    void persistAll(List<Role> roleList);

    /**
     * @Name update
     * @Author HuoJiaJin
     * @Description 更新大部分字段
     * @Date 2021/5/23 22:54
     * @Param [role]
     * @Return void
     **/
    Integer update(Role role);

    /**
     * @Name findById
     * @Author HuoJiaJin
     * @Description 根据名称查询
     * @Date 2021/5/23 22:34
     * @Param [id]
     * @Return howard.cinema.core.dao.entity.acl.Role
     **/
    Role findByName(@Param("name") String name);

    /**
     * @Name updateDelete
     * @Author HuoJiaJin
     * @Description 删除更新
     * @Date 2021/5/24 2:16
     * @Param [id]
     * @Return void
     **/
    void updateDelete(@Param("id")String id);

    /**
     * @name: listByCinemaId
     * @description: 根据影城ID查询角色
     * @author: huojiajin
     * @para: [cinemaId]
     * @return: java.util.List<howard.cinema.core.dao.entity.acl.Role>
    **/
    List<Role> listByCinemaId(@Param("cinemaId")String cinemaId);
}
