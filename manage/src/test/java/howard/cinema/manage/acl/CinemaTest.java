package howard.cinema.manage.acl;

import com.github.pagehelper.PageInfo;
import howard.cinema.core.manage.model.CommonResponse;
import howard.cinema.core.manage.tools.JsonTools;
import howard.cinema.core.manage.tools.httpclient.HttpClientHelper;
import howard.cinema.manage.ApplicationTests;
import howard.cinema.manage.model.acl.role.RoleAddRequest;
import howard.cinema.manage.model.common.CommonPageRequest;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * @name: TestCinema
 * @description: 影城管理test
 * @author: huojiajin
 * @time: 2021/5/24 15:29
 */
public class CinemaTest extends ApplicationTests {

    private static final String token = "";

    @Test
    public void roleQuery() throws IOException {

        CommonPageRequest request = new CommonPageRequest();
        request.setPageNo(2);
        request.setPageSize(20);
        request.setToken(token);
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
    public void roleedit() throws IOException {
        RoleAddRequest request = new RoleAddRequest();
        request.setName("测试1");
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
}
