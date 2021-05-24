package howard.cinema.manage.model.common;

import howard.cinema.core.dao.entity.common.BaseEntity;

import java.util.List;

/**
 * @name: CommonListReponse
 * @description: 公共list返回参数
 * @author: huojiajin
 * @time: 2021/5/24 14:45
 */
public class CommonListReponse<T> extends BaseEntity {

    private List<T> result;//返回结果集

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }
}
