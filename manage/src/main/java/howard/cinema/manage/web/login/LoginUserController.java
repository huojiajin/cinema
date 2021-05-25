package howard.cinema.manage.web.login;

import howard.cinema.manage.manage.login.LoginUserManager;
import howard.cinema.manage.model.login.UserInfoEditRequest;
import howard.cinema.manage.model.acl.user.UserPasswordEditRequest;
import howard.cinema.manage.model.common.CommonTokenRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @name: LoginUserController
 * @description: 登陆用户相关操作Controller
 * @author: huojiajin
 * @time: 2020/7/15 15:12
 */
@RestController
@RequestMapping("/user/common")
public class LoginUserController {

    @Autowired
    private LoginUserManager manager;

    @PostMapping("/pwedit")
    public String passwordEdit(@Valid @RequestBody UserPasswordEditRequest request){
        return  manager.passwordEdit(request);
    }

    @PostMapping("/loginout")
    public String loginout(@Valid @RequestBody CommonTokenRequest request){
        return  manager.loginout(request);
    }

    @PostMapping("/info")
    public String info(@Valid @RequestBody CommonTokenRequest request){
        return manager.info(request);
    }

    @PostMapping("/infoedit")
    public String infoEdit(@Valid @RequestBody UserInfoEditRequest request){
        return manager.infoEdit(request);
    }

    @PostMapping("/infoedit")
    public String getResources(@Valid @RequestBody CommonTokenRequest request){
        return manager.getResourceList(request);
    }
}
