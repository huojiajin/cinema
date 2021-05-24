package howard.cinema.manage.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @name: MyFilterConfig
 * @description: 自定义拦截器配置
 * @author: huojiajin
 * @time: 2020/5/29 11:36
 */
@Configuration
public class MyFilterConfig {

    @Bean
    public FilterRegistrationBean<MyFilter> Filters() {

        FilterRegistrationBean<MyFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new MyFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.setName("MyFilter");
        return registrationBean;
    }
}
