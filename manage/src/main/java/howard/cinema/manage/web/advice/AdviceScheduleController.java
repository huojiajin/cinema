package howard.cinema.manage.web.advice;

import howard.cinema.manage.manage.advice.AdviceMaterialManager;
import howard.cinema.manage.manage.advice.AdviceScheduleManager;
import howard.cinema.manage.model.advice.material.AdviceMaterialAddRequest;
import howard.cinema.manage.model.advice.material.AdviceMaterialQueryRequest;
import howard.cinema.manage.model.advice.schedule.AdviceScheduleAddRequest;
import howard.cinema.manage.model.advice.schedule.AdviceScheduleQueryRequest;
import howard.cinema.manage.model.common.CommonIdRequest;
import howard.cinema.manage.web.common.MyBaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @name: AdviceScheduleController
 * @description: 广告排期Controller
 * @author: huojiajin
 * @time: 2021/6/2 14:26
 */
@RestController
@RequestMapping("/advice/schedule")
public class AdviceScheduleController extends MyBaseController {

    @Autowired
    private AdviceScheduleManager manager;

    @PostMapping("/query")
    public String query(@RequestBody AdviceScheduleQueryRequest request){
        return manager.query(request);
    }

    @PostMapping("/add")
    public String add(@RequestBody AdviceScheduleAddRequest request){
        return manager.add(request);
    }

    @PostMapping("/delete")
    public String delete(@RequestBody CommonIdRequest request){
        return manager.delete(request);
    }

    @PostMapping("/cinemalist")
    public String getCinemaList(@RequestBody CommonIdRequest request){
        return manager.getCinemaList(request);
    }

    @PostMapping("/materiallist")
    public String getMaterialList(@RequestBody CommonIdRequest request){
        return manager.getMaterialList(request);
    }
}
