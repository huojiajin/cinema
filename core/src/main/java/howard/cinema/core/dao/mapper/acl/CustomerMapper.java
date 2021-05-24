package howard.cinema.core.dao.mapper.acl;

import howard.cinema.core.dao.entity.acl.Customer;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: CustomerMapper
 * @Description: 客户Mapper
 * @Author HuoJiaJin
 * @Date 2021/5/24 0:53
 * @Version 1.0
 **/
@Component
public interface CustomerMapper {

    List<Customer> findAll();

    Customer findById(String id);
}
