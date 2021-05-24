package howard.cinema.core.dao.mapper.acl;

import howard.cinema.core.dao.entity.acl.SystemInfo;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 *@ClassName SystemInfoMapper.java
 *@Description 系统日志表Mapper
 *@Author HuoJiaJin
 *@Date 2021/5/23 21:27
 *@Version 1.0
 **/
@Component
public interface SystemInfoMapper {

    /**
     * @Name persist
     * @Author HuoJiaJin
     * @Description 保存单个实体
     * @Date 2021/5/23 22:36
     * @Param [info]
     * @Return void
     **/
    void persist(SystemInfo info);
}
