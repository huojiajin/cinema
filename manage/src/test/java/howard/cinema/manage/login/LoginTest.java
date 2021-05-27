package howard.cinema.manage.login;

import howard.cinema.core.manage.model.CommonResponse;
import howard.cinema.core.manage.tools.JsonTools;
import howard.cinema.core.manage.tools.httpclient.HttpClientHelper;
import howard.cinema.manage.ApplicationTests;
import howard.cinema.manage.model.login.LoginRequest;
import howard.cinema.manage.model.login.LoginResponse;
import howard.cinema.manage.model.login.VerifyResponse;
import org.junit.jupiter.api.Test;
import org.springframework.util.Base64Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @name: LoginTest
 * @description: 登陆Test
 * @author: huojiajin
 * @time: 2020/5/28 17:12
 */
public class LoginTest extends ApplicationTests {

    @Test
    void verify() throws URISyntaxException, IOException {
        String url = "http://localhost/manage/login/verify";
//        String url = "http://39.106.226.73/manage/login/verify";

        String responseStr = HttpClientHelper.httpGet(new URI(url), "UTF-8");
        CommonResponse<VerifyResponse> response = JsonTools.json2Object(responseStr, CommonResponse.class, VerifyResponse.class);
        VerifyResponse verifyResponse = response.getData();

        byte[] b = Base64Utils.decodeFromString(verifyResponse.getVerifyImage());
        for (int i = 0; i < b.length; ++i) {
            if (b[i] < 0) {// 调整异常数据
                b[i] += 256;
            }
        }
        String fileName = "C:\\Users\\霍佳进\\Desktop\\out.png";
        File file = new File(fileName);
        if (!file.exists()){
            file.createNewFile();
        }
        OutputStream out = new FileOutputStream(file);
        out.write(b);
        out.flush();
        out.close();
        System.out.println(verifyResponse.getVerifyId());
    }

    @Test
    public void login() throws IOException {
        LoginRequest request = new LoginRequest();
        request.setLoginName("admin");
//        request.setPassword("123456");
//        request.setVerifyId("b6f08a35e43c4e87a8c3bc321bcb995b");
        request.setVerifyCode("4rwd");
//        String url = "http://39.106.226.73/manage/login/login";
        String url = "http://localhost/manage/login/login";

        String responseStr = HttpClientHelper.jsonPost(url, request.toJson());
        echo(responseStr);
        CommonResponse<LoginResponse> response = JsonTools.json2Object(responseStr, CommonResponse.class, LoginResponse.class);
        LoginResponse loginResponse = response.getData();
        echo(loginResponse);
    }
}
