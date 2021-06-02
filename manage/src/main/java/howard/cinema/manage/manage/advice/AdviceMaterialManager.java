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
    String query(AdviceMaterialQueryRequest request);

    String add(AdviceMaterialAddRequest request);

    String delete(CommonIdRequest request);
}
