package howard.cinema.core.manage.annotation;

import java.lang.annotation.*;

/**
 *@ClassName MyScheduler
 *@Description 自定义定时任务注解
 *@Author HuoJiaJin
 *@Date 2020/7/24 11:13
 *@Version 1.0
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyScheduler {

    String name() default "";
    String cron() default "0 0 0 1/1 * ?";
    String description() default "";
}
