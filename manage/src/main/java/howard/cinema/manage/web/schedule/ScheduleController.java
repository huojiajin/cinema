package howard.cinema.manage.web.schedule;

import howard.cinema.manage.web.common.MyBaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @name: ScheduleController
 * @description: 定时任务相关Controller
 * @author: huojiajin
 * @time: 2020/9/16 23:59
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController extends MyBaseController {

//    @Autowired
//    private ScheduleManager manager;
//
//    @GetMapping("/{name}")
//    public String runByName(@PathVariable("name")String name, HttpServletRequest request, HttpServletResponse response){
//        try {
//            manager.runByName(name);
//        } catch (Exception e) {
//            logger.error("", e);
//            return toLogString("调用定时任务{}失败!", name);
//        }
//        return toLogString("调用定时任务{}成功！", name);
//    }
}
