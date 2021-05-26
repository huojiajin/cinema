package howard.cinema.manage;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@SpringBootApplication
@ServletComponentScan
@MapperScan(value = "howard.cinema.core.dao.mapper", annotationClass = Component.class)
@ComponentScan(value = {"howard.cinema.core.dao.mapper","howard.cinema.manage"})
public class ManageApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManageApplication.class, args);
    }

}
