package howard.cinema.manage.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @name: DruidConfig
 * @description: druid连接池配置
 * @author: huojiajin
 * @time: 2020/5/25 10:27
 */
@Configuration
@EntityScan(basePackages = {"howard.cinema.core.dao.entity"})
@EnableTransactionManagement
public class DruidConfig {


}
