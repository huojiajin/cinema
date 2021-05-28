package howard.cinema.manage.model.acl.user;

import howard.cinema.manage.model.common.CommonPageRequest;

/**
 * @ClassName: UserQueryRequest
 * @Description: 查询用户Request
 * @Author HuoJiaJin
 * @Date 2021/5/28 0:09
 * @Version 1.0
 **/
public class UserQueryRequest extends CommonPageRequest {

    private String cinemaId;//影城ID
    private String loginName;//登录名
    private String name;//用户名

    public String getCinemaId() {
        return cinemaId;
    }

    public void setCinemaId(String cinemaId) {
        this.cinemaId = cinemaId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
