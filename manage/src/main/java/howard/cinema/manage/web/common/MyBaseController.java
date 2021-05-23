package howard.cinema.manage.web.common;

import howard.cinema.core.dao.entity.acl.SystemInfo;
import howard.cinema.core.dao.mapper.acl.SystemInfoMapper;
import howard.cinema.core.manage.common.CommonAbstract;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

/**
 * @name: MyBaseController
 * @description: 控制层基础类
 * @author: huojiajin
 * @time: 2020/5/27 10:47
 */
public class MyBaseController extends CommonAbstract {

    @Autowired
    private SystemInfoMapper systemInfoMapper;

    protected void addSysLog(String info, String userId){
        SystemInfo systemInfo = new SystemInfo();
        systemInfo.setInsertTime(LocalDateTime.now());
        systemInfo.setUserId(userId);
        systemInfo.setInfo(info);
        systemInfoMapper.persist(systemInfo);
    }

}
