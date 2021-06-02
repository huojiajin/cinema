package howard.cinema.manage.manage.advice;

import howard.cinema.manage.model.advice.material.upload.AdviceMaterialCheckRequest;
import howard.cinema.manage.model.advice.material.upload.AdviceMaterialUploadRequest;

/**
 * @name: AdviceMaterialUploadManager
 * @description: 广告素材上传Manager
 * @author: huojiajin
 * @time: 2021/6/1 9:38
 */
public interface AdviceMaterialUploadManager {

    /**
     * @name: checkFileMd5
     * @description: 校验文件完整性
     * @author: huojiajin
     * @para: [request]
     * @return: java.lang.String
    **/
    String checkFileMd5(AdviceMaterialCheckRequest request);

    /**
     * @name: uploadFile
     * @description: 文件上传
     * @author: huojiajin
     * @para: [request]
     * @return: String
    **/
    String uploadFile(AdviceMaterialUploadRequest request);
}
