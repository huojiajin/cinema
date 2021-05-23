package howard.cinema.core.dao.mapper.acl;

import howard.cinema.core.dao.entity.acl.Customer;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: CustomerMapper
 * @Description: 客户Mapper
 * @Author HuoJiaJin
 * @Date 2021/5/24 0:53
 * @Version 1.0
 **/
@Repository
public interface CustomerMapper {

    List<Customer> findAll();
}
