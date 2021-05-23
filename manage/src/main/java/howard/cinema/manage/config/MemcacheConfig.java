package howard.cinema.manage.config;

import net.spy.memcached.MemcachedClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * @name: MemcacheClient
 * @description: memcache配置文件
 * @author: huojiajin
 * @time: 2020/5/25 15:10
 */
@Configuration
public class MemcacheConfig {

    @Value("${memcache.ip}")
    private String ip;
    @Value("${memcache.port}")
    private int port;

    @Bean
    public MemcachedClient getClient() {
        MemcachedClient memcachedClient = null;
        try {
            memcachedClient  = new MemcachedClient(new InetSocketAddress(ip, port));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return memcachedClient;
    }
}
