package howard.cinema.core.dao.mapper.advice;

import howard.cinema.core.dao.entity.advice.AdviceScheduleCinema;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @name: AdviceScheduleCinemaMapper
 * @description: 广告排期-相关影院管理
 * @author: huojiajin
 * @time: 2021/6/2 15:12
 */
@Component
public interface AdviceScheduleCinemaMapper {

    /**
     * @name: persistAll
     * @description: 批量保存
     * @author: huojiajin
     * @para: [cinemaList]
     * @return: void
    **/
    void persistAll(List<AdviceScheduleCinema> cinemaList);

    /**
     * @name: findByScheduleId
     * @description: 根据排期ID查询
     * @author: huojiajin
     * @para: [scheduleId]
     * @return: void
    **/
    List<AdviceScheduleCinema> findByScheduleId(@Param("scheduleId")String scheduleId);
}
