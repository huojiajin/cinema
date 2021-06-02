package howard.cinema.manage.web.advice;

import howard.cinema.manage.manage.advice.AdviceMaterialManager;
import howard.cinema.manage.model.advice.material.AdviceMaterialAddRequest;
import howard.cinema.manage.model.advice.material.AdviceMaterialQueryRequest;
import howard.cinema.manage.model.common.CommonIdRequest;
import howard.cinema.manage.web.common.MyBaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @name: AdviceMaterialController
 * @description: 广告素材Controller
 * @author: huojiajin
 * @time: 2021/6/2 14:26
 */
@RestController
@RequestMapping("/advice/material")
public class AdviceMaterialController extends MyBaseController {

    @Autowired
    private AdviceMaterialManager manager;

    @PostMapping("/query")
    public String query(@RequestBody AdviceMaterialQueryRequest request){
        return manager.query(request);
    }

    @PostMapping("/add")
    public String add(@RequestBody AdviceMaterialAddRequest request){
        return manager.add(request);
    }

    @PostMapping("/delete")
    public String delete(@RequestBody CommonIdRequest request){
        return manager.delete(request);
    }
}
