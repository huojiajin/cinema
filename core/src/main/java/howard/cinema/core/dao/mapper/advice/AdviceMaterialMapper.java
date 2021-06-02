package howard.cinema.core.dao.mapper.advice;

import howard.cinema.core.dao.entity.advice.AdviceMaterial;
import howard.cinema.core.manage.model.advice.AdviceMaterialListRequest;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 *@name: AdviceMaterialMapper
 *@description: 广告素材Mapper
 *@author: huojiajin
 *@time: 2021/5/31 15:19
**/
@Component
public interface AdviceMaterialMapper {

    /**
     * @name: findById
     * @description: 根据ID查询实体
     * @author: huojiajin
     * @para: [id]
     * @return: howard.cinema.core.dao.entity.advice.AdviceMaterial
    **/
    AdviceMaterial findById(@Param("id")String id);

    /**
     * @name: findAll
     * @description: 查询全部
     * @author: huojiajin
     * @para: []
     * @return: java.util.List<howard.cinema.core.dao.entity.advice.AdviceMaterial>
    **/
    List<AdviceMaterial> findAll();

    /**
     * @name: list
     * @description: 按条件查询
     * @author: huojiajin
     * @para: [request]
     * @return: java.util.List<howard.cinema.core.dao.entity.advice.AdviceMaterial>
    **/
    List<AdviceMaterial> list(AdviceMaterialListRequest request);

    /**
     * @name: persist
     * @description: 保存实体
     * @author: huojiajin
     * @para: [material]
     * @return: void
    **/
    void persist(AdviceMaterial material);

    /**
     * @name: update
     * @description: 更新实体
     * @author: huojiajin
     * @para: [material]
     * @return: void
    **/  
    void update(AdviceMaterial material);

    /**
     * @name: updateDelete
     * @description: 删除实体
     * @author: huojiajin
     * @para: [id]
     * @return: void
    **/
    void updateDelete(@Param("id") String id, @Param("updateTime")LocalDateTime updateTime);
}
