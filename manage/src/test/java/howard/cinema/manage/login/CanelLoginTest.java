package howard.cinema.manage.login;

import howard.cinema.core.dao.dict.acl.ResourceType;
import howard.cinema.core.dao.entity.acl.RoleResource;
import howard.cinema.core.dao.entity.acl.User;
import howard.cinema.core.dao.mapper.acl.RoleResourceMapper;
import howard.cinema.core.manage.tools.JsonTools;
import howard.cinema.manage.ManageApplicationTests;
import howard.cinema.manage.manage.tools.MyMecachedPrefix;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.internal.OperationFuture;
import org.apache.commons.compress.utils.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @ClassName: CanelLoginTest
 * @Description: 注销登录
 * @Author HuoJiaJin
 * @Date 2021/5/25 0:17
 * @Version 1.0
 **/
public class CanelLoginTest extends ManageApplicationTests {

    @Autowired
    private RoleResourceMapper roleResourceMapper;

    @Autowired
    private MemcachedClient client;

    @Test
    public void canel() throws Exception {
        String token = "0a27360e12914373b457cb2daedbac80";
        String userKey = MyMecachedPrefix.loginTokenPrefix + token;
                Object userObject = client.get(userKey);
        if (userObject == null) {
            System.out.println("未找到对应的缓存");
            return;
        }
        String userJson = (String) userObject;
        User user = JsonTools.json2Object(userJson, User.class);
        OperationFuture<Boolean> delete = client.delete(MyMecachedPrefix.loginTokenPrefix + token);
        System.out.println(delete.get());
        OperationFuture<Boolean> delete2 = client.delete(MyMecachedPrefix.loginResourcePrefix + user.getId());
        System.out.println(delete2.get());
    }

    @Test
    public void initAdminResource(){
        List<RoleResource> list = Lists.newArrayList();
        for (ResourceType type : ResourceType.values()) {
            RoleResource entity = new RoleResource();
            entity.setRoleId("123456");
            entity.setResourceType(type);
            list.add(entity);
        }

        roleResourceMapper.persistAll(list);

    }
}
