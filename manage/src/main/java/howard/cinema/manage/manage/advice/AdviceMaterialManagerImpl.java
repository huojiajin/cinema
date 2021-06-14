package howard.cinema.manage.manage.advice;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import howard.cinema.core.dao.entity.acl.User;
import howard.cinema.core.dao.entity.advice.AdviceMaterial;
import howard.cinema.core.dao.mapper.advice.AdviceMaterialMapper;
import howard.cinema.core.manage.model.CommonResponse;
import howard.cinema.core.manage.model.advice.AdviceMaterialListRequest;
import howard.cinema.core.manage.tools.FileTools;
import howard.cinema.manage.manage.common.AbstractManager;
import howard.cinema.manage.model.advice.material.AdviceMaterialAddRequest;
import howard.cinema.manage.model.advice.material.AdviceMaterialAddResponse;
import howard.cinema.manage.model.advice.material.AdviceMaterialQueryRequest;
import howard.cinema.manage.model.common.CommonIdRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @name: AdviceMaterialManagerImpl
 * @description: 广告素材管理相关Manager
 * @author: huojiajin
 * @time: 2021/6/2 10:59
 */
@Service
public class AdviceMaterialManagerImpl extends AbstractManager implements AdviceMaterialManager {

    @Autowired
    private AdviceMaterialMapper materialMapper;
    @Value("${upload.viewPath}")
    private String viewPath;

    @Override
    public String query(AdviceMaterialQueryRequest request){
        CommonResponse<PageInfo> response = new CommonResponse<>();

        AdviceMaterialListRequest listRequest = new AdviceMaterialListRequest();
        BeanUtils.copyProperties(request, listRequest);
        //开始查询
        PageHelper.startPage(request.getPageNo(), request.getPageSize());
        List<AdviceMaterial> materialList = materialMapper.list(listRequest);
        //拼装查看素材详情链接
        materialList = materialList.stream().map(m -> {
            if (hasText(m.getFilePath())) {
                String[] split = m.getFilePath().split("/");
                String fileName = split[split.length - 1];
                m.setFilePath(viewPath + m.getId() + "/" + fileName);
            }
            return m;
        }).collect(Collectors.toList());
        PageInfo<AdviceMaterial> page = new PageInfo<>(materialList);
        return response.setData(page);
    }

    @Override
    public String add(AdviceMaterialAddRequest request){
        CommonResponse response = new CommonResponse();
        AdviceMaterialAddResponse data = new AdviceMaterialAddResponse();
        AdviceMaterial material = new AdviceMaterial();
        BeanUtils.copyProperties(request, material);
        User user = getUser(request.getToken());
        material.setOperatorId(user.getId());
        materialMapper.persist(material);
        addSysLog("添加素材" + request.getName(), request.getToken(), material.getId());
        response.setMessage("添加素材成功");
        data.setId(material.getId());
        return response.setData(data);
    }

    @Override
    public String delete(CommonIdRequest request){
        CommonResponse response = new CommonResponse();
        //TODO 判断素材是否在用
        materialMapper.updateDelete(request.getId(), LocalDateTime.now());
        String fileDirPath = System.getProperty("user.dir") + File.separator + "material" + File.separator;
        try {
            FileTools.deleteDir(new File(fileDirPath));
        } catch (Exception e) {
           logger.error("", e);
        }
        addSysLog("删除素材" + request.getId(), request.getToken(), request.getId());
        return response.setMessage("删除素材成功");
    }


}
