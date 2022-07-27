package hello.springmvc.basic.request;

import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Slf4j
@Controller
public class RequestParamController {

    //jar 사용하면 webapp 경로 사용 못하므로
    @RequestMapping("/request-param-v1")
    public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));
        log.info("username={},age={}", username, age);

        response.getWriter().write("ok");//void 타입일 때 출력하기 좋네
    }

    //request.getParameter와 동일
    @ResponseBody
    @RequestMapping("/request-param-v2")
    public String requestParamV2(
            @RequestParam("username") String memberName,
            @RequestParam("age") int memberAge) {

        log.info("username={}, age={}", memberName, memberAge);
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/request-param-v3")
    public String requestParamV3(
            @RequestParam String username,
            @RequestParam int age) {

        log.info("username={}, age={}", username, age);
        return "ok";
    }

    //요청 파라미터 명과 동일한 경우에는 아래와 같이 가능
    @ResponseBody
    @RequestMapping("/request-param-v4")
    public String requestParamV4(String username, int age) {
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    //@RequestParam의 required 옵션은 true가 defalut. 해당 파라미터가 없으면 오류
    //int는 null 값 안됨. Integer 객체로 받아야 오류 안 뜸
    //?username= 으로 전송할 경우 빈 문자가 들어감
    @ResponseBody
    @RequestMapping("/request-param-required")
    public String requestParamRequired(
            @RequestParam(required = true) String username,
            @RequestParam(required = false) Integer age) {
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/request-param-default")
    public String requestParamDefault(
            @RequestParam(required = true, defaultValue = "guest") String username,
            @RequestParam(required = false) Integer age) {
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    //파라미터 값이 1개로 확실하면 map을 사용하지만 두개 이상일 경우 multivalue map을 사용하자
    @ResponseBody
    @RequestMapping("/request-param-map")
    public String requestParamMap(
            @RequestParam Map<String, Object> paramMap) {
        log.info("username={}, age={}", paramMap.get("username"), paramMap.get("age"));
        return "ok";
    }

    /*
    @ResponseBody
    @RequestMapping("/model-attribute-v1")
    public String modelAttributeV1(@RequestParam String username, @RequestParam int age) {
        HelloData helloData = new HelloData();
        helloData.setUsername(username);
        helloData.setAge(age);

//        log.info("username={}, age={}", helloData.getUsername(),helloData.getAge());
        log.info("helloData={}",helloData);
        return "ok";

    }*/

    //위의 객체 생성 set을 전부 처리해줌, 타입에 맞지 않는 데이터가 들어오면 bind exception 뜸
    @ResponseBody
    @RequestMapping("/model-attribute-v1")
    public String modelAttributeV1(@ModelAttribute HelloData helloData) {
        log.info("helloData={}",helloData);
        return "ok";

    }

    //model attribute 생략 가능
    //string, int integer 같은 단순 타입은 request param 생략
    //나머지(argument resolver가 지정해둔 타입 이외)는 model attribute
    @ResponseBody
    @RequestMapping("/model-attribute-v2")
    public String modelAttributeV2(HelloData helloData) {
        log.info("helloData={}",helloData);
        return "ok";

    }

    //Html Form 형식이 아니라 Http 메시지 바디를 통해 데이터가 날아오는 경우
    //@RequestParam, @ModelAttribute를 사용 불가



}
