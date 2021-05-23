package howard.cinema.manage.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @name: AppContextManager
 * @description: 上下文关联获取bean
 * @author: huojiajin
 * @time: 2020/5/27 16:23
 */

public class AppContextManager implements ApplicationContextAware {

    private static ApplicationContext applicationContext;


    public void setApplicationContext(ApplicationContext appContext) throws BeansException {
        applicationContext = appContext;
    }



    public static <T> T getBean(Class<T> requiredType) {
        return applicationContext.getBean(requiredType);
    }

}
