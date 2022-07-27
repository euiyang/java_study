package hello.springmvc.basic.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Controller
public class RequestBodyJsonController {
    private ObjectMapper objectMapper=new ObjectMapper();

    @PostMapping("/request-body-json-v1")
    public void requestBodyJsonV1(HttpServletRequest request, HttpServletResponse response)throws IOException{
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody", messageBody);

        //message body의 json 형태를 helloData 객체에 매핑핑
       HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
        log.info("uesrname={}, age={}",helloData.getUsername(),helloData.getAge());

        response.getWriter().write("ok");
    }

    @ResponseBody
    @PostMapping("/request-body-json-v2")
    public String requestBodyJsonV2(@RequestBody String messageBody)throws IOException{
        log.info("messageBody", messageBody);

        //message body의 json 형태를 helloData 객체에 매핑핑
        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
        log.info("uesrname={}, age={}",helloData.getUsername(),helloData.getAge());

        return "ok";
    }

    //@RequstBody는 Http 메시지 컨버터(MappingJackson2HttpMessageConverter)가
    // 메시지 바디의 내용을 원하는 객체로 변환. 단 @RequestBody 생략 불가, 생략시
    // @ModelAttribute 가 적용되어 요청 파라미터 처리
    @ResponseBody
    @PostMapping("/request-body-json-v3")
    public String requestBodyJsonV3(@RequestBody HelloData helloData){
        log.info("username={}, age={}",helloData.getUsername(),helloData.getAge());

        return "ok";
    }

    //HttpEntity 사용도 가능
    @ResponseBody
    @PostMapping("/request-body-json-v4")
    public String requestBodyJsonV4(HttpEntity<HelloData> httpEntity){
        HelloData helloData=httpEntity.getBody();
        log.info("username={}, age={}",helloData.getUsername(),helloData.getAge());

        return "ok";
    }


    //response의 경우에 @ResponseBody를 사용하면 해당 객체를 HTTP 메시지 바디에 Json 형태로
    //넣는다.
    //@RequestBody 요청: Json->Http 메시지 컨버터-> 객체
    //@ResponseBody 응답: 객체->Http 메시지 컨버터-> Json
    @ResponseBody
    @PostMapping("/request-body-json-v5")
    public HelloData requestBodyJsonV5(@RequestBody HelloData helloData){
        log.info("username={}, age={}",helloData.getUsername(),helloData.getAge());

        return helloData;
    }
}
