package howard.cinema.manage.web.acl;

import howard.cinema.manage.manage.acl.RoleManager;
import howard.cinema.manage.model.acl.role.RoleAddRequest;
import howard.cinema.manage.model.acl.role.RoleEditRequest;
import howard.cinema.manage.model.acl.role.RoleResourceRequest;
import howard.cinema.manage.model.common.CommonIdRequest;
import howard.cinema.manage.model.common.CommonPageRequest;
import howard.cinema.manage.model.common.CommonRequest;
import howard.cinema.manage.web.common.MyBaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @name: RoleController
 * @description: 角色管理Controller
 * @author: huojiajin
 * @time: 2020/5/27 10:46
 */
@RestController
@RequestMapping("/role")
public class RoleController extends MyBaseController {

    @Autowired
    private RoleManager roleManager;

    @PostMapping("/query")
    public String query(@RequestBody CommonPageRequest pageRequest, HttpServletRequest request){
        return roleManager.query(pageRequest);
    }

    @PostMapping("/list")
    public String query(@RequestBody CommonRequest request){
        return roleManager.list(request);
    }

    @PostMapping("/add")
    public String add(@RequestBody RoleAddRequest addRequest){
        return roleManager.add(addRequest);
    }

    @PostMapping("/edit")
    public String edit(@RequestBody RoleEditRequest editRequest){

        return roleManager.update(editRequest);
    }

    @PostMapping("/delete")
    public String delete(@RequestBody CommonIdRequest deleteRequest){
        return roleManager.delete(deleteRequest);
    }

    @PostMapping("/resource/config")
    public String resourceConfig(@RequestBody RoleResourceRequest resourceRequest){
        return roleManager.resourceConfig(resourceRequest);
    }

    @PostMapping("/resource/list")
    public String resourceList(@RequestBody CommonIdRequest request){
        return roleManager.resourceList(request);
    }
}
