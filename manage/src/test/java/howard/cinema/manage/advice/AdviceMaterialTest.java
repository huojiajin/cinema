package howard.cinema.manage.advice;

import howard.cinema.core.manage.model.CommonResponse;
import howard.cinema.core.manage.tools.JsonTools;
import howard.cinema.core.manage.tools.httpclient.HttpClientHelper;
import howard.cinema.manage.ApplicationTests;
import howard.cinema.manage.model.advice.material.AdviceMaterialQueryRequest;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * @ClassName: AdviceMaterialTest
 * @Description: 广告素材测试
 * @Author HuoJiaJin
 * @Date 2021/6/2 23:27
 * @Version 1.0
 **/
public class AdviceMaterialTest extends ApplicationTests {

    @Test
    public void query() throws IOException {

        AdviceMaterialQueryRequest request = new AdviceMaterialQueryRequest();
        request.setToken("9929574aa25f45478ad818d38ccfb024");
        request.setResourceCode(11);
        request.setPageSize(1);
        String url = "http://localhost/manage/advice/material/query";
//        String url = "http://39.106.226.73:8080/manage/advice/material/query";

        String responseStr = HttpClientHelper.jsonPost(url, request.toJson());
        echo(responseStr);
        CommonResponse response = JsonTools.json2Object(responseStr, CommonResponse.class);
        echo(response);
    }
}
