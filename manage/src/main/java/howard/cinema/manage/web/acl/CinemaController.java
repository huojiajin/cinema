package howard.cinema.manage.web.acl;

import howard.cinema.manage.manage.acl.CinemaManager;
import howard.cinema.manage.model.acl.cinema.CinemaAddRequest;
import howard.cinema.manage.model.acl.cinema.CinemaEditRequest;
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
import javax.validation.Valid;

/**
 * @name: CinemaController
 * @description: 影城管理Controller
 * @author: huojiajin
 * @time: 2020/5/27 10:46
 */
@RestController
@RequestMapping("/cinema")
public class CinemaController extends MyBaseController {

    @Autowired
    private CinemaManager cinemaManager;

    @PostMapping("/query")
    public String query(@Valid @RequestBody CommonPageRequest pageRequest, HttpServletRequest request){
        return cinemaManager.query(pageRequest);
    }

    @PostMapping("/list")
    public String query(@Valid @RequestBody CommonRequest request){
        return cinemaManager.list(request);
    }

    @PostMapping("/add")
    public String add(@Valid @RequestBody CinemaAddRequest addRequest){
        return cinemaManager.add(addRequest);
    }

    @PostMapping("/edit")
    public String edit(@Valid @RequestBody CinemaEditRequest editRequest){

        return cinemaManager.update(editRequest);
    }

    @PostMapping("/disable")
    public String disable(@Valid @RequestBody CommonIdRequest deleteRequest){
        return cinemaManager.disable(deleteRequest);
    }

    @PostMapping("/enable")
    public String enable(@Valid @RequestBody CommonIdRequest deleteRequest){
        return cinemaManager.enable(deleteRequest);
    }
}
