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

    @Override
    public void setApplicationContext(ApplicationContext appContext) throws BeansException {
        applicationContext = appContext;
        if (applicationContext != null) {
            System.out.println(">>>>>>>>>>>>>>成功初始化 applicationContext");
        } else {
            System.out.println(">>>>>>>>>>>>>>applicationContext 初始化失败");
        }
    }

    public static <T> T getBean(Class<T> requiredType) {
        return applicationContext.getBean(requiredType);
    }

    public static <T> T getBean(String beanName) {
        return (T) applicationContext.getBean(beanName);
    }

    public static <T> T getBean(String beanName, Class<T> requiredType) {
        return applicationContext.getBean(beanName, requiredType);
    }
}
