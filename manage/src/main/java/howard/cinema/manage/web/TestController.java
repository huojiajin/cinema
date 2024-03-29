package howard.cinema.manage.web;

import com.rabbitmq.client.AMQP;
import howard.cinema.manage.web.common.MyBaseController;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;

/**
 * @name: TestController
 * @description: 测试
 * @author: huojiajin
 * @time: 2020/5/25 17:50
 */
@RestController
public class TestController extends MyBaseController {

    @Value("${spring.application.name}")
    private String name;
    @Autowired
    private ConfigurableApplicationContext run;


    @RequestMapping(value = "/health",method = {RequestMethod.GET,RequestMethod.HEAD})
    public String heathTest(){

        printUrl();
        return "已打印所有链接";
    }

    @PostConstruct
    private void printUrl() {
        logger.info("======正在打印已注册链接======");
        //获取restcontroller注解的类名
        String[] beanNamesForAnnotation = run.getBeanNamesForAnnotation(RestController.class);

        //获取类对象
        for (String str : beanNamesForAnnotation) {
            Object bean = run.getBean(str);
            Class<?> forName = bean.getClass();
            System.out.println(forName.getName());

            //获取requestmapping注解的类
            RequestMapping declaredAnnotation = forName.getAnnotation(RequestMapping.class);

            String url_path = "";
            if (declaredAnnotation != null) {
                String[] value = (declaredAnnotation.value());
                //获取类的url路径
                url_path = value[0];
                for (Method method : forName.getDeclaredMethods()) {
                    RequestMapping annotation2 = method.getAnnotation(RequestMapping.class);
                    if (annotation2 != null) {
                        url_path += annotation2.value()[0];
                        System.out.println("方法路径:" + url_path + ";方法名:" + method.getName());
                    }
                    url_path = value[0];
                }

                for (Method method : forName.getDeclaredMethods()) {
                    PostMapping annotation2 = method.getAnnotation(PostMapping.class);
                    if (annotation2 != null) {
                        url_path += annotation2.value()[0];
                        System.out.println("方法路径:" + url_path + ";方法名:" + method.getName());
                    }
                    url_path = value[0];
                }
            }
        }
        logger.info("======打印完毕======");
    }
}
