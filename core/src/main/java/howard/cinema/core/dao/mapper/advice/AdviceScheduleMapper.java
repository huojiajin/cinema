package howard.cinema.core.dao.mapper.advice;

import howard.cinema.core.dao.entity.advice.AdviceSchedule;
import howard.cinema.core.manage.model.advice.AdviceScheduleListRequest;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 *@name: AdviceScheduleMapper
 *@description: 广告排期Mapper
 *@author: huojiajin
 *@time: 2021/5/31 15:19
**/
@Component
public interface AdviceScheduleMapper {

    /**
     * @name: findById
     * @description: 根据ID查询实体
     * @author: huojiajin
     * @para: [id]
     * @return: howard.cinema.core.dao.entity.advice.AdviceSchedule
    **/
    AdviceSchedule findById(@Param("id")String id);

    /**
     * @name: findAll
     * @description: 查询全部
     * @author: huojiajin
     * @para: []
     * @return: java.util.List<howard.cinema.core.dao.entity.advice.AdviceSchedule>
    **/
    List<AdviceSchedule> findAll();

    /**
     * @name: list
     * @description: 按条件查询
     * @author: huojiajin
     * @para: [request]
     * @return: java.util.List<howard.cinema.core.dao.entity.advice.AdviceSchedule>
    **/
    List<AdviceSchedule> list(AdviceScheduleListRequest request);

    /**
     * @name: persist
     * @description: 保存实体
     * @author: huojiajin
     * @para: [schedule]
     * @return: void
    **/
    void persist(AdviceSchedule schedule);

    /**
     * @name: updateDelete
     * @description: 删除
     * @author: huojiajin
     * @para: [id, updateTime]
     * @return: void
    **/
    void updateDelete(@Param("id") String id, @Param("updateTime")LocalDateTime updateTime);
}
