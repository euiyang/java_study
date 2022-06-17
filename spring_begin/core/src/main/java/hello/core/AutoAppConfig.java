package hello.core;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        //basepackages= "hello.core.member"
        // 또는 basepackageClasses로 하면 해당 패키지, 클래스 부터 탐색
        //default는 위의 패키지 명 package hello.core부터 등록.
        excludeFilters = @ComponentScan.Filter(type= FilterType.ANNOTATION,classes = Configuration.class)
)
public class AutoAppConfig {

}
