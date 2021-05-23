package howard.cinema.manage.model.login;

import howard.cinema.core.dao.entity.common.BaseEntity;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

/**
 * @name: VerifyModel
 * @description: 图片验证码返回Model
 * @author: huojiajin
 * @time: 2020/5/27 15:09
 */
public class VerifyResponse extends BaseEntity {

    private String verifyImage;//验证码图片base64流
    private String verifyId;//验证码对应ID

    public VerifyResponse() {
    }

    public VerifyResponse(BufferedImage bufferedImage, String verifyId) throws IOException {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(bufferedImage, "png", outputStream);
            Base64.Encoder encoder = Base64.getEncoder();
            this.verifyImage = encoder.encodeToString(outputStream.toByteArray());
        } finally {
            outputStream.close();
        }
        this.verifyId = verifyId;
    }

    public String getVerifyImage() {
        return verifyImage;
    }

    public void setVerifyImage(String verifyImage) {
        this.verifyImage = verifyImage;
    }

    public String getVerifyId() {
        return verifyId;
    }

    public void setVerifyId(String verifyId) {
        this.verifyId = verifyId;
    }
}
