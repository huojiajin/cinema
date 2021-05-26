package howard.cinema.manage.login;

import howard.cinema.core.manage.model.CommonResponse;
import howard.cinema.core.manage.tools.JsonTools;
import howard.cinema.core.manage.tools.httpclient.HttpClientHelper;
import howard.cinema.manage.ApplicationTests;
import howard.cinema.manage.model.common.CommonTokenRequest;
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
 * @name: LoginUserTest
 * @description: 登陆后用户个人相关Test
 * @author: huojiajin
 * @time: 2020/5/28 17:12
 */
public class LoginUserTest extends ApplicationTests {

    @Test
    void info() throws IOException {
        CommonTokenRequest request = new CommonTokenRequest();
//        request.setToken("c1027beedad34e3e93ff738f1f7a85b7");

        String url = "http://localhost/manage/common/user/info";
        String responseStr = HttpClientHelper.jsonPost(url, request.toJson());
        System.out.println(responseStr);
    }

    @Test
    void loginout() throws IOException {
        CommonTokenRequest request = new CommonTokenRequest();
        request.setToken("2703636d53af4759a4223e6576a8cb1c");

        String url = "http://localhost/manage/common/user/loginout";
        String responseStr = HttpClientHelper.jsonPost(url, request.toJson());
        System.out.println(responseStr);
    }
}
