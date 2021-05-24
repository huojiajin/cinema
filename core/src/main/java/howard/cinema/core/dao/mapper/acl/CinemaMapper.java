package howard.cinema.core.dao.mapper.acl;

import howard.cinema.core.dao.entity.acl.Cinema;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *@ClassName CinemaMapper.java
 *@Description 影城实体Mapper
 *@Author HuoJiaJin
 *@Date 2021/5/24 0:47
 *@Version 1.0
 **/
@Component
public interface CinemaMapper {

    /**
     * @Name findById
     * @Author HuoJiaJin
     * @Description 根据ID查询实体
     * @Date 2021/5/24 0:49
     * @Param [id]
     * @Return howard.cinema.core.dao.entity.acl.Cinema
     **/
    Cinema findById(@Param("id")String id);

    /**
     * @Name findAll
     * @Author HuoJiaJin
     * @Description 查询全部实体
     * @Date 2021/5/24 0:49
     * @Param []
     * @Return java.util.List<howard.cinema.core.dao.entity.acl.Cinema>
     **/
    List<Cinema> findAll();

    /**
     * @Name listUse
     * @Author HuoJiaJin
     * @Description 查询在用的影城
     * @Date 2021/5/24 0:49
     * @Param []
     * @Return java.util.List<howard.cinema.core.dao.entity.acl.Cinema>
     **/
    List<Cinema> listUse();

    /**
     * @Name findByParentId
     * @Author HuoJiaJin
     * @Description 根据上级影院查询实体
     * @Date 2021/5/24 0:50
     * @Param [parentId]
     * @Return java.util.List<howard.cinema.core.dao.entity.acl.Cinema>
     **/
    List<Cinema> findByParentId(@Param("parentId")String parentId);

    /**
     * @Name persist
     * @Author HuoJiaJin
     * @Description 保存单个实体
     * @Date 2021/5/24 1:06
     * @Param [cinema]
     * @Return void
     **/
    void persist(Cinema cinema);

    /**
     * @Name update
     * @Author HuoJiaJin
     * @Description 更新实体
     * @Date 2021/5/24 0:50
     * @Param [cinema]
     * @Return void
     **/
    void update(Cinema cinema);

    /**
     * @Name updateStop
     * @Author HuoJiaJin
     * @Description 更新是否停用
     * @Date 2021/5/24 0:51
     * @Param [stop]
     * @Return void
     **/
    void updateStop(@Param("hasDelete") boolean hasDelete, @Param("id") String id);

    /**
     * @name: findByName
     * @description: 根据名称查询
     * @author: huojiajin
     * @para: [name]
     * @return: howard.cinema.core.dao.entity.acl.Cinema
    **/
    Cinema findByName(@Param("name")String name);
}
