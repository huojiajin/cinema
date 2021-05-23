package howard.cinema.manage.model.common;

import howard.cinema.core.dao.entity.common.BaseEntity;

/**
 * @ClassName CommonExportResponse
 * @Description 导出文件通用Response
 * @Author HuoJiaJin
 * @Date 2020/6/22 0:45
 * @Version 1.0
 **/
public class CommonExportResponse extends BaseEntity {

    private String resultFile;//结果文件Base64

    public String getResultFile() {
        return resultFile;
    }

    public void setResultFile(String resultFile) {
        this.resultFile = resultFile;
    }
}
