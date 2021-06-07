package howard.cinema.manage.web.advice;

import howard.cinema.manage.manage.advice.AdviceMaterialUploadManager;
import howard.cinema.manage.model.advice.material.upload.AdviceMaterialCheckRequest;
import howard.cinema.manage.model.advice.material.upload.AdviceMaterialUploadRequest;
import howard.cinema.manage.web.common.MyBaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

/**
 *@name: AdviceMaterialUploadController
 *@description: 广告素材文件上传Controller
 *@author: huojiajin
 *@time: 2021/6/2 10:58
**/
@RestController
@RequestMapping("/advice/material")
public class AdviceMaterialUploadController extends MyBaseController {

    @Autowired
    private AdviceMaterialUploadManager manager;

    @PostMapping(value = "/check")
    public String checkFileMd5(@Valid @RequestBody AdviceMaterialCheckRequest request) {
        return manager.checkFileMd5(request);
    }

    @PostMapping(value = "/upload")
    public String fileUpload(@Valid AdviceMaterialUploadRequest request) {
        return manager.uploadFile(request);
    }
}
