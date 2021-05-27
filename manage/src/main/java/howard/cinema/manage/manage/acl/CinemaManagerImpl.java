package howard.cinema.manage.manage.acl;

import howard.cinema.core.dao.dict.acl.ErrorType;
import howard.cinema.core.dao.entity.acl.Cinema;
import howard.cinema.core.dao.entity.acl.Customer;
import howard.cinema.core.dao.mapper.acl.CinemaMapper;
import howard.cinema.core.dao.mapper.acl.CustomerMapper;
import howard.cinema.core.dao.mapper.acl.RoleMapper;
import howard.cinema.core.manage.model.CommonResponse;
import howard.cinema.manage.manage.common.AbstractManager;
import howard.cinema.manage.model.acl.cinema.CinemaAddRequest;
import howard.cinema.manage.model.acl.cinema.CinemaEditRequest;
import howard.cinema.manage.model.acl.cinema.CinemaQueryModel;
import howard.cinema.manage.model.acl.cinema.CinemaQueryRequest;
import howard.cinema.manage.model.common.CommonIdRequest;
import howard.cinema.manage.model.common.CommonListReponse;
import howard.cinema.manage.model.common.CommonRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @name: CinemaManagerImpl
 * @description: 影城相关ManagerImpl
 * @author: huojiajin
 * @time: 2020/5/28 10:48
 */
@Service
public class CinemaManagerImpl extends AbstractManager implements CinemaManager {

    @Autowired
    private CinemaMapper cinemaMapper;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public String query(CinemaQueryRequest request){
        CommonResponse<CommonListReponse<CinemaQueryModel>> response = new CommonResponse<>();
        CommonListReponse<CinemaQueryModel> data = new CommonListReponse<>();
        List<CinemaQueryModel> childCinemaList = findChildCinemaList(request.getParentId());//最顶层影院父类ID为空
        data.setResult(childCinemaList);
        response.setData(data);
        return response.toJson();
    }

    /**
     * @name: findChildCinemaList
     * @description: 递归获取子影城列表
     * @author: huojiajin
     * @para: [parentId]
     * @return: java.util.List<howard.cinema.manage.model.acl.cinema.CinemaQueryResponse>
    **/
    private List<CinemaQueryModel> findChildCinemaList(String parentId){
        List<Cinema> cinemaList = cinemaMapper.findByParentId(parentId);
        return cinemaList.stream().map(c -> {
            CinemaQueryModel model = new CinemaQueryModel();
            BeanUtils.copyProperties(c, model);
            model.setHasUse(!c.isHasDelete());
            Customer customer = customerMapper.findById(c.getCustomerId());
            model.setCustomerName(customer.getName());
            List<CinemaQueryModel> childCinemaList = findChildCinemaList(c.getId());
            model.setResult(childCinemaList);
            return model;
        }).collect(Collectors.toList());
    }

    @Override
    public String list(CommonRequest request){
        CommonResponse<CommonListReponse<Cinema>> response = new CommonResponse<>();
        CommonListReponse<Cinema> data = new CommonListReponse<>();
        List<Cinema> list = cinemaMapper.listUse();
        data.setResult(list);
        response.setData(data);
        return response.toJson();
    }

    @Override
    public String add(CinemaAddRequest addRequest){
        CommonResponse response = new CommonResponse();
        String name = addRequest.getName();

        Cinema oldCinema = cinemaMapper.findByName(name);
        if (oldCinema != null){
            return response.setError(ErrorType.VALID, "影城名称重复");
        }
        Cinema cinema = new Cinema();
        BeanUtils.copyProperties(addRequest, cinema);
        cinemaMapper.persist(cinema);
        addSysLog("添加影城" + name, addRequest.getToken(), cinema.getId());
        response.setMessage("添加影城成功");
        return response.toJson();
    }

    @Override
    public String update(CinemaEditRequest editRequest){
        CommonResponse response = new CommonResponse();
        String name = editRequest.getName();
        Cinema oldCinema = cinemaMapper.findByName(name);
        if (oldCinema != null){
            return response.setError(ErrorType.VALID, "影城名称重复");
        }
        Cinema byId = cinemaMapper.findById(editRequest.getId());
        if (byId == null){
            return response.setError(ErrorType.VALID, "要更新的影城不存在");
        }
        Cinema cinema = new Cinema();
        BeanUtils.copyProperties(editRequest, cinema);
        cinema.setUpdateTime(LocalDateTime.now());
        cinemaMapper.update(cinema);
        addSysLog("修改影城" + name, editRequest.getToken(), editRequest.getId());
        response.setMessage("修改影城成功");
        return response.toJson();
    }

    @Override
    public String disable(CommonIdRequest deleteRequest) {
        CommonResponse response = new CommonResponse();
//        List<Role> roleList = roleMapper.listByCinemaId(deleteRequest.getId());
//        if (!isEmpty(roleList)){
//            return response.setError(ErrorType.VALID, "影城下有在用角色，不可停用");
//        }
        cinemaMapper.updateStop(true, deleteRequest.getId());
        addSysLog("停用影城", deleteRequest.getToken(), deleteRequest.getId());
        response.setMessage("停用影城成功");
        return response.toJson();
    }

    @Override
    public String enable(CommonIdRequest deleteRequest) {
        CommonResponse response = new CommonResponse();
//        List<Role> roleList = roleMapper.listByCinemaId(deleteRequest.getId());
//        if (!isEmpty(roleList)){
//            return response.setError(ErrorType.VALID, "影城下有在用角色，不可停用");
//        }
        cinemaMapper.updateStop(false, deleteRequest.getId());
        addSysLog("启用影城", deleteRequest.getToken(), deleteRequest.getId());
        response.setMessage("启用影城成功");
        return response.toJson();
    }
}
