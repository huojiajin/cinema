package howard.cinema.manage.manage.common;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @ClassName: MultipartFileJsonHandler
 * @Description: json转multipartFile处理
 * @Author HuoJiaJin
 * @Date 2021/6/6 23:23
 * @Version 1.0
 **/
public class MultipartFileJsonHandler extends JsonDeserializer<MultipartFile> {

    @Override
    public MultipartFile deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        return null;
    }
}
