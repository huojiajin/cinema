package howard.cinema.manage.manage.advice;

import howard.cinema.manage.model.advice.material.AdviceMaterialAddRequest;
import howard.cinema.manage.model.advice.material.AdviceMaterialQueryRequest;
import howard.cinema.manage.model.common.CommonIdRequest;

/**
 * @name: AdviceMaterialManager
 * @description: 广告素材管理Manager
 * @author: huojiajin
 * @time: 2021/6/2 10:59
 */
public interface AdviceMaterialManager {
    /**
     * @Name query
     * @Author HuoJiaJin
     * @Description 查询
     * @Date 2021/6/7 23:35
     * @Param [request]
     * @Return java.lang.String
     **/
    String query(AdviceMaterialQueryRequest request);

    /**
     * @Name add
     * @Author HuoJiaJin
     * @Description 添加
     * @Date 2021/6/7 23:35
     * @Param [request]
     * @Return java.lang.String
     **/
    String add(AdviceMaterialAddRequest request);

    /**
     * @Name delete
     * @Author HuoJiaJin
     * @Description 删除
     * @Date 2021/6/7 23:35
     * @Param [request]
     * @Return java.lang.String
     **/
    String delete(CommonIdRequest request);
}
