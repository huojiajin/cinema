package howard.cinema.manage.model.common;

import howard.cinema.core.dao.entity.common.BaseEntity;

/**
 * @ClassName PaperTemplateResponse
 * @Description 试卷模板下载Response
 * @Author HuoJiaJin
 * @Date 2020/6/21 17:44
 * @Version 1.0
 **/
public class CommonTemplateResponse extends BaseEntity {

    private String template;//模板文件base64

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }
}
