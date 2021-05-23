package howard.cinema.manage.web.acl;

import howard.cinema.core.dao.entity.common.BaseEntity;
import howard.cinema.manage.manage.acl.UserManager;
import howard.cinema.manage.model.acl.user.UserAddRequest;
import howard.cinema.manage.model.acl.user.UserEditRequest;
import howard.cinema.manage.model.common.CommonIdRequest;
import howard.cinema.manage.model.common.CommonPageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @name: UserController
 * @description: 用户Controller
 * @author: huojiajin
 * @time: 2020/5/28 14:59
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseEntity {

    @Autowired
    private UserManager userManager;

    @PostMapping("/query")
    public String query(@RequestBody CommonPageRequest request){
        return userManager.query(request);
    }

    @PostMapping("/add")
    public String add(@RequestBody UserAddRequest addRequest){
        return userManager.add(addRequest);
    }

    @PostMapping("/edit")
    public String edit(@RequestBody UserEditRequest editRequest){
        return userManager.update(editRequest);
    }

    @PostMapping("/stop")
    public String stop(@RequestBody CommonIdRequest deleteRequest){
        return userManager.stop(deleteRequest);
    }

    @PostMapping("/start")
    public String start(@RequestBody CommonIdRequest startRequest){
        return userManager.start(startRequest);
    }
}
