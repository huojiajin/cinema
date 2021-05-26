package howard.cinema.manage.manage.common;

import howard.cinema.core.dao.dict.acl.ErrorType;
import howard.cinema.core.manage.model.CommonResponse;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * @name: MyExceptionHandler
 * @description: 我的异常处理
 * @author: huojiajin
 * @time: 2021/5/26 16:46
 */
@RestControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(BindException.class)
    public String validationBodyException(BindException e){
        CommonResponse response = new CommonResponse();
        StringBuilder sb = new StringBuilder("");
        for (FieldError fieldError : e.getFieldErrors()) {
            sb.append(fieldError.getDefaultMessage() + ";");
        }
        response.setError(ErrorType.VALID, sb.toString());
        return response.toJson();
    }
}
