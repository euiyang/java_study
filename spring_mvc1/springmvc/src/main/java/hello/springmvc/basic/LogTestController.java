package hello.springmvc.basic;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@Controller String 반환시 view로 반단 후 진행
@RestController
@Slf4j// 아래 로그 인스턴스 자동 생성
public class LogTestController {
//    private final Logger log= LoggerFactory.getLogger(getClass());
//    private static final Logger log=LoggerFactory.getLogger(LogTestController.class);

    @RequestMapping("/log-test")
    public String logTest(){
        String name="Spring";

        //name = Spring
        System.out.println("name = " + name);

        //아래의 경우는 + 합 연산이 사용하지 않아도 일어남-> 리소스 낭비
//        log.info(" info log="+name);

        log.trace("trace log={}",name);
        log.debug("debug log={}",name);
        log.info("info log={}",name);
        log.warn("warn log={}",name);
        log.error("error log={}",name);

        //2022-07-25 16:53:18.804  INFO 26340 --- [nio-8080-exec-3] hello.springmvc.basic.LogTestController  :  info log=Spring
        log.info(" info log={}",name);

        return"ok";
    }
}
