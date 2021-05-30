package howard.cinema.manage.acl;

import howard.cinema.core.dao.dict.acl.CinemaType;
import howard.cinema.core.dao.dict.acl.PosType;
import howard.cinema.core.manage.model.CommonResponse;
import howard.cinema.core.manage.tools.JsonTools;
import howard.cinema.core.manage.tools.httpclient.HttpClientHelper;
import howard.cinema.manage.ApplicationTests;
import howard.cinema.manage.model.acl.cinema.CinemaAddRequest;
import howard.cinema.manage.model.acl.cinema.CinemaEditRequest;
import howard.cinema.manage.model.common.CommonIdRequest;
import howard.cinema.manage.model.common.CommonRequest;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * @name: TestCinema
 * @description: 影城管理test
 * @author: huojiajin
 * @time: 2021/5/24 15:29
 */
public class CinemaTest extends ApplicationTests {

    private static final String token = "111ce50148ec45e6ba78d6f668928c38";

    @Test
    public void cinemaQuery() throws IOException {

        CommonRequest request = new CommonRequest();
        request.setToken(token);
        request.setResourceCode(11);
        String url = "http://localhost/manage/cinema/query";

        String responseStr = HttpClientHelper.jsonPost(url, request.toJson());
        echo(responseStr);
        CommonResponse response = JsonTools.json2Object(responseStr, CommonResponse.class);
        echo(response);
    }

    @Test
    public void cinemaList() throws IOException {

        CommonRequest request = new CommonRequest();
        request.setToken(token);
        request.setResourceCode(11);
        String url = "http://localhost/manage/cinema/list";

        String responseStr = HttpClientHelper.jsonPost(url, request.toJson());
        CommonResponse response = JsonTools.json2Object(responseStr, CommonResponse.class);
        echo(response);
    }

    @Test
    public void cinemaAdd() throws IOException {
        CinemaAddRequest request = new CinemaAddRequest();
        request.setToken(token);
        request.setResourceCode(11);
        request.setParentId("123456");
        request.setName("测试");
        request.setCode("000001");
        request.setType(CinemaType.CINEMA);
        request.setPosType(PosType.DINGXIN);
        request.setCustomerId("123456");
        request.setList(0);
        String url = "http://localhost/manage/cinema/add";
        String responseStr = HttpClientHelper.jsonPost(url, request.toJson());
        CommonResponse response = JsonTools.json2Object(responseStr, CommonResponse.class);
        echo(response);
    }

    @Test
    public void cinemaEdit() throws IOException {
        CinemaEditRequest request = new CinemaEditRequest();
        request.setToken(token);
        request.setResourceCode(11);
        request.setId("4a1b696e5d0c41dc9a9d14dae1d41481");
        request.setParentId("123456");
        request.setName("测试123");
        request.setCode("0000001");
        request.setType(CinemaType.CINEMA);
        request.setPosType(PosType.DINGXIN);
        request.setCustomerId("123456");
        request.setInfo("测试修改");
        request.setList(1);
        String url = "http://localhost/manage/cinema/edit";
        String responseStr = HttpClientHelper.jsonPost(url, request.toJson());
        CommonResponse response = JsonTools.json2Object(responseStr, CommonResponse.class);
        echo(response);
    }

    @Test
    public void disable() throws Exception{
        CommonIdRequest request = new CommonIdRequest();
        request.setToken(token);
        request.setResourceCode(11);
        request.setId("4a1b696e5d0c41dc9a9d14dae1d41481");

        String url = "http://localhost/manage/cinema/disable";
        String responseStr = HttpClientHelper.jsonPost(url, request.toJson());
        CommonResponse response = JsonTools.json2Object(responseStr, CommonResponse.class);
        echo(response);
    }

    @Test
    public void enable() throws Exception{
        CommonIdRequest request = new CommonIdRequest();
        request.setToken(token);
        request.setResourceCode(11);
        request.setId("4a1b696e5d0c41dc9a9d14dae1d41481");

        String url = "http://localhost/manage/cinema/enable";
        String responseStr = HttpClientHelper.jsonPost(url, request.toJson());
        CommonResponse response = JsonTools.json2Object(responseStr, CommonResponse.class);
        echo(response);
    }

    @Test
    public void customers() throws Exception{
        CommonRequest request = new CommonRequest();
        request.setToken(token);
        request.setResourceCode(11);

        String url = "http://localhost/manage/cinema/enable";
        String responseStr = HttpClientHelper.jsonPost(url, request.toJson());
        CommonResponse response = JsonTools.json2Object(responseStr, CommonResponse.class);
        echo(response);
    }
}
