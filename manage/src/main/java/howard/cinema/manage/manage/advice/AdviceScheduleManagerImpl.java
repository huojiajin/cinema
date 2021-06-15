package howard.cinema.manage.manage.advice;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import howard.cinema.core.dao.entity.acl.User;
import howard.cinema.core.dao.entity.advice.AdviceMaterial;
import howard.cinema.core.dao.entity.advice.AdviceSchedule;
import howard.cinema.core.dao.entity.advice.AdviceScheduleCinema;
import howard.cinema.core.dao.entity.advice.AdviceScheduleMaterial;
import howard.cinema.core.dao.mapper.advice.AdviceMaterialMapper;
import howard.cinema.core.dao.mapper.advice.AdviceScheduleCinemaMapper;
import howard.cinema.core.dao.mapper.advice.AdviceScheduleMapper;
import howard.cinema.core.dao.mapper.advice.AdviceScheduleMaterialMapper;
import howard.cinema.core.manage.model.CommonResponse;
import howard.cinema.core.manage.model.advice.AdviceScheduleListRequest;
import howard.cinema.core.manage.tools.MyTimeTools;
import howard.cinema.manage.manage.common.AbstractManager;
import howard.cinema.manage.model.advice.schedule.AdviceScheduleCinemaModel;
import howard.cinema.manage.model.advice.schedule.AdviceScheduleAddRequest;
import howard.cinema.manage.model.advice.schedule.AdviceScheduleMaterialModel;
import howard.cinema.manage.model.advice.schedule.AdviceScheduleQueryRequest;
import howard.cinema.manage.model.common.CommonIdRequest;
import howard.cinema.manage.model.common.CommonListReponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @name: AdviceScheduleManagerImpl
 * @description: 广告排期管理相关ManagerImpl
 * @author: huojiajin
 * @time: 2021/6/2 15:21
 */
@Service
public class AdviceScheduleManagerImpl extends AbstractManager implements AdviceScheduleManager {

    @Autowired
    private AdviceScheduleMapper scheduleMapper;
    @Autowired
    private AdviceScheduleCinemaMapper scheduleCinemaMapper;
    @Autowired
    private AdviceScheduleMaterialMapper scheduleMaterialMapper;
    @Autowired
    private AdviceMaterialMapper materialMapper;

    @Override
    public String query(AdviceScheduleQueryRequest request){
        CommonResponse<PageInfo<AdviceSchedule>> response = new CommonResponse<>();
        AdviceScheduleListRequest listRequest = new AdviceScheduleListRequest();
        BeanUtils.copyProperties(request, listRequest);
        PageHelper.startPage(request.getPageNo(), request.getPageSize());
        //开始查询
        PageHelper.startPage(request.getPageNo(), request.getPageSize());
        List<AdviceSchedule> materialList = scheduleMapper.list(listRequest);
        PageInfo<AdviceSchedule> page = new PageInfo<>(materialList);
        return response.setData(page);
    }

    @Override
    public String add(AdviceScheduleAddRequest request){
        CommonResponse<String> response = new CommonResponse<>();
        AdviceSchedule schedule = new AdviceSchedule();
        BeanUtils.copyProperties(request, schedule);
        schedule.setStartTime(MyTimeTools.strToDate(request.getStartTime()));
        schedule.setEndTime(MyTimeTools.strToDate(request.getEndTime()));
        User user = getUser(request.getToken());
        schedule.setOperatorId(user.getId());
        schedule.setOperatorName(user.getName());
        //获取关联影城列表
        List<AdviceScheduleCinema> cinemaList = request.getCinemaList().stream().map(c -> {
            AdviceScheduleCinema cinema = new AdviceScheduleCinema();
            BeanUtils.copyProperties(c, cinema);
            cinema.setScheduleId(schedule.getId());
            return cinema;
        }).collect(Collectors.toList());
        //获取关联素材列表
        List<AdviceScheduleMaterial> materialList = request.getMaterialList().stream().map(m -> {
            AdviceScheduleMaterial material = new AdviceScheduleMaterial();
            BeanUtils.copyProperties(m, material);
            material.setScheduleId(schedule.getId());
            AdviceMaterial byId = materialMapper.findById(m.getMaterialId());
            material.setFilePath(byId.getName());
            return material;
        }).collect(Collectors.toList());

        //保存实体
        persist(schedule, cinemaList, materialList);
        addSysLog("添加排期" + request.getName(), request.getToken(), schedule.getId());
        return response.setMessage("添加排期成功");
    }

    @Transactional
    private void persist(AdviceSchedule schedule, List<AdviceScheduleCinema> cinemaList, List<AdviceScheduleMaterial> materialList) {
        scheduleMapper.persist(schedule);
        scheduleCinemaMapper.persistAll(cinemaList);
        scheduleMaterialMapper.persistAll(materialList);
    }

    @Override
    public String delete(CommonIdRequest request){
        CommonResponse<String> response = new CommonResponse<>();
        scheduleMapper.updateDelete(request.getId(), LocalDateTime.now());
        addSysLog("删除排期" + request.getId(), request.getToken(), request.getId());
        return response.setMessage("删除排期成功");
    }

    @Override
    public String getCinemaList(CommonIdRequest request){
        CommonResponse<CommonListReponse<AdviceScheduleCinemaModel>> response = new CommonResponse<>();
        CommonListReponse<AdviceScheduleCinemaModel> data = new CommonListReponse<>();
        List<AdviceScheduleCinema> cinemaList = scheduleCinemaMapper.findByScheduleId(request.getId());
        List<AdviceScheduleCinemaModel> cinemaModelList = cinemaList.stream().map(c -> {
            AdviceScheduleCinemaModel model = new AdviceScheduleCinemaModel();
            BeanUtils.copyProperties(c, model);
            return model;
        }).collect(Collectors.toList());
        data.setResult(cinemaModelList);
        return response.setData(data);
    }

    @Override
    public String getMaterialList(CommonIdRequest request){
        CommonResponse<CommonListReponse<AdviceScheduleMaterialModel>> response = new CommonResponse<>();
        CommonListReponse<AdviceScheduleMaterialModel> data = new CommonListReponse<>();
        List<AdviceScheduleMaterial> materialList = scheduleMaterialMapper.findByScheduleId(request.getId());
        List<AdviceScheduleMaterialModel> materialModelList = materialList.stream().map(c -> {
            AdviceScheduleMaterialModel model = new AdviceScheduleMaterialModel();
            BeanUtils.copyProperties(c, model);
            return model;
        }).collect(Collectors.toList());
        data.setResult(materialModelList);
        return response.setData(data);
    }
}
