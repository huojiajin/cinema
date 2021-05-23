package howard.cinema.manage.model.acl.role;

import howard.cinema.core.dao.entity.acl.Role;

/**
 * @ClassName: RoleModel
 * @Description: 角色查询用Model
 * @Author HuoJiaJin
 * @Date 2021/5/24 3:18
 * @Version 1.0
 **/
public class RoleModel extends Role {

    private String cinemaName;

    public String getCinemaName() {
        return cinemaName;
    }

    public void setCinemaName(String cinemaName) {
        this.cinemaName = cinemaName;
    }
}
