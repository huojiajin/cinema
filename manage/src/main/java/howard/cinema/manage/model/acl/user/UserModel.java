package howard.cinema.manage.model.acl.user;

import howard.cinema.core.dao.entity.acl.User;

/**
 * @ClassName: UserModel
 * @Description: 用户查询Model
 * @Author HuoJiaJin
 * @Date 2021/5/24 3:20
 * @Version 1.0
 **/
public class UserModel extends User {

    private String roleName;//角色名称
    private String cinemaName;//影城名称

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getCinemaName() {
        return cinemaName;
    }

    public void setCinemaName(String cinemaName) {
        this.cinemaName = cinemaName;
    }
}
