package howard.cinema.manage.acl;

import com.github.pagehelper.PageInfo;
import howard.cinema.core.manage.model.CommonResponse;
import howard.cinema.core.manage.tools.JsonTools;
import howard.cinema.core.manage.tools.httpclient.HttpClientHelper;
import howard.cinema.manage.ApplicationTests;
import howard.cinema.manage.model.acl.role.RoleAddRequest;
import howard.cinema.manage.model.acl.role.RoleResourceRequest;
import howard.cinema.manage.model.common.CommonPageRequest;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * @name: RoleTest
 * @description: 角色管理测试
 * @author: huojiajin
 * @time: 2020/6/17 10:29
 */
public class RoleTest extends ApplicationTests {

    @Test
    public void roleQuery() throws IOException {

        CommonPageRequest request = new CommonPageRequest();
        request.setPageNo(2);
        request.setPageSize(20);
        request.setToken("5e9ad2689fe64e0a8dfab3f06370033d");
        request.setResourceCode(12);
        String url = "http://localhost/manage/role/query";
//        String url = "http://39.106.226.73/manage/role/query";

        String responseStr = HttpClientHelper.jsonPost(url, request.toJson());
        echo(responseStr);
        CommonResponse<PageInfo> response = JsonTools.json2Object(responseStr, CommonResponse.class, PageInfo.class);
        PageInfo page = response.getData();
        echo(page);
    }

    @Test
    public void roleAdd() throws IOException {
        RoleAddRequest request = new RoleAddRequest();
        request.setName("测试");
        request.setToken("1a8db6051b2846fd918ae851ffb93d3e");
        request.setInfo("测试用");
        request.setList(1);
        request.setResourceCode(12);
        String url = "http://localhost/manage/role/add";
//        String url = "http://39.106.226.73/manage/role/add";
        String responseStr = HttpClientHelper.jsonPost(url, request.toJson());
        echo(responseStr);
        CommonResponse response = JsonTools.json2Object(responseStr, CommonResponse.class);
        echo(response);
    }

    @Test
    public void roleResourceConfig() throws IOException {

        RoleResourceRequest request = new RoleResourceRequest();
        request.setToken("daae4849b6ed46fabb527f8448aa5b07");
        request.setResourceCode(12);
        request.setRoleId("123456");
        request.setResourceCodeList(Lists.newArrayList(1,11,12,13,2,21,22,23));
        String url = "http://localhost/manage/role/resource/config";
//        String url = "http://39.106.226.73/manage/role/resource/config";

        String responseStr = HttpClientHelper.jsonPost(url, request.toJson());
        echo(responseStr);
        CommonResponse response = JsonTools.json2Object(responseStr, CommonResponse.class);
        echo(response);
    }
}
