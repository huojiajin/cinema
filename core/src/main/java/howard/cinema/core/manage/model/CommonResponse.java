package howard.cinema.core.manage.model;

import howard.cinema.core.dao.dict.acl.ErrorType;
import howard.cinema.core.dao.entity.common.BaseEntity;

/**
 * @name: CommonResponse
 * @description: 公用返回Model
 * @author: huojiajin
 * @time: 2020/5/27 15:31
 */
public class CommonResponse<T extends BaseEntity> extends BaseEntity {

    private boolean success = true;
    private int errCode;//错误码
    private String message;//错误信息
    private T data;//数据json

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String setError(ErrorType errorType){
        this.success = false;
        this.errCode = errorType.getErrCode();
        this.message = errorType.getErrMsg();
        return this.toJson();
    }

    public String setError(ErrorType errorType, String message){
        this.success = false;
        this.errCode = errorType.getErrCode();
        this.message = errorType.getErrMsg() + ":" + message;
        return this.toJson();
    }
}
