package howard.cinema.manage.manage.advice;

import howard.cinema.manage.model.advice.schedule.AdviceScheduleAddRequest;
import howard.cinema.manage.model.advice.schedule.AdviceScheduleQueryRequest;
import howard.cinema.manage.model.common.CommonIdRequest;

/**
 *@name: AdviceScheduleManager
 *@description: 广告排期管理相关Manager
 *@author: huojiajin
 *@time: 2021/6/2 15:21
**/
public interface AdviceScheduleManager {
    /**
     * @Name query
     * @Author HuoJiaJin
     * @Description 查询
     * @Date 2021/6/2 17:32
     * @Param [request]
     * @Return java.lang.String
     **/
    String query(AdviceScheduleQueryRequest request);

    /**
     * @Name add
     * @Author HuoJiaJin
     * @Description 添加
     * @Date 2021/6/2 17:32
     * @Param [request]
     * @Return java.lang.String
     **/
    String add(AdviceScheduleAddRequest request);

    /**
     * @Name delete
     * @Author HuoJiaJin
     * @Description 删除
     * @Date 2021/6/2 17:32
     * @Param [request]
     * @Return java.lang.String
     **/
    String delete(CommonIdRequest request);

    /**
     * @Name getCinemaList
     * @Author HuoJiaJin
     * @Description 获取关联影城列表
     * @Date 2021/6/2 17:32
     * @Param [request]
     * @Return java.lang.String
     **/
    String getCinemaList(CommonIdRequest request);

    /**
     * @Name getMaterialList
     * @Author HuoJiaJin
     * @Description 获取关联材料列表
     * @Date 2021/6/2 17:32
     * @Param [request]
     * @Return java.lang.String
     **/
    String getMaterialList(CommonIdRequest request);
}
