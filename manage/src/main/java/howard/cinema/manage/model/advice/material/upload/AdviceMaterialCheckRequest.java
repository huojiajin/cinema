package howard.cinema.manage.model.advice.material.upload;

import howard.cinema.manage.model.common.CommonIdRequest;

import javax.validation.constraints.NotBlank;

/**
 * @name: AdviceMaterialCheckRequest
 * @description: 校验文件完整性
 * @author: huojiajin
 * @time: 2021/6/1 10:33
 */
public class AdviceMaterialCheckRequest extends CommonIdRequest {

    @NotBlank(message = "请填写md5校验码")
    private String md5;//md5校验码

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }
}
