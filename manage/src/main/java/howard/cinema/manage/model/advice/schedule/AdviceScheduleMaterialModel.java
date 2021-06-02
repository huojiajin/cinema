package howard.cinema.manage.model.advice.schedule;

import howard.cinema.core.dao.entity.common.BaseEntity;

/**
 * @name: AdviceScheduleAddCinemaModel
 * @description: 广告排期添加-素材列表
 * @author: huojiajin
 * @time: 2021/6/2 15:32
 */
public class AdviceScheduleMaterialModel extends BaseEntity {

    private String materialId;//影城ID
    private String materialName;//影城名称
    private String list;//排序
    private String filePath;//文件路径

    public String getMaterialId() {
        return materialId;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getList() {
        return list;
    }

    public void setList(String list) {
        this.list = list;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
