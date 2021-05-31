package howard.cinema.core.dao.mapper.advice;

import howard.cinema.core.dao.entity.advice.AdviceMaterial;
import howard.cinema.core.manage.model.advice.AdviceMaterialListRequest;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 *@name: AdviceMaterialMapper
 *@description: 广告素材Mapper
 *@author: huojiajin
 *@time: 2021/5/31 15:19
**/
public interface AdviceMaterialMapper {

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
     * @name: updateUpload
     * @description: 上传文件更新
     * @author: huojiajin
     * @para: [id, filePath, updateTime]
     * @return: void
    **/
    void updateUpload(@Param("id")String id, @Param("filePath")String filePath, @Param("updateTime")LocalDateTime updateTime);

    /**
     * @name: updateDelete
     * @description: 删除实体
     * @author: huojiajin
     * @para: [id]
     * @return: void
    **/
    void updateDelete(@Param("id") String id);
}
