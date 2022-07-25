package hello.servlet.web.springmvc.v1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller// 내부 component annotation으로 스프링 빈에 들어감
//@Component// 스프링 빈 등록 @RequestMapping// controller와 같이 클래스 레벨에 있을 경우 처리 가능한 핸들러로 인식함.
public class SpringMemberFormControllerV1 {
    @RequestMapping("/springmvc/v1/members/new-form")
    public ModelAndView process(){
        return new ModelAndView("new-form");
    }
}
