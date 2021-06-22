package howard.cinema.terminal.config;

import howard.cinema.core.manage.common.CommonAbstract;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *@name: ContextConfig
 *@description: 上下文配置
 *@author: huojiajin
 *@time: 2020/5/27 16:24
**/
@Configuration
public class ContextConfig extends CommonAbstract {

	
	@Bean
	public AppContextManager appContextManager(ApplicationContext applicationContext) {
		AppContextManager appContextManager = new AppContextManager();
		appContextManager.setApplicationContext(applicationContext);
		return appContextManager;
	}
}
