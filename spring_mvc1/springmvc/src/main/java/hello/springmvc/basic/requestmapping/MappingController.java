package hello.springmvc.basic.requestmapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class MappingController {
    private Logger log= LoggerFactory.getLogger(getClass());

    //배열 형태로 여러개 넣을 수 있음.
    @RequestMapping({"hello-basic","hello-go"})
    public String helloBasic(){
        log.info("helloBasic");
        return "ok";
    }

    @RequestMapping(value="/mapping-get-v1",method= RequestMethod.GET)
    public String mappingGetV1(){
        log.info("mappingGetV1");
        return "ok";
    }

    @GetMapping(value="/mapping-get-v2")
    public String mappingGetV2 (){
        log.info("mapping-get-v2");
        return "ok";
    }

    //{} 값으로 들오는 것은 @PathVariable(파라미터 명)으로 꺼낼 수 있음.
    //PathVariable과 파라미터 명이 동일한 경우 생략 가능. @PathVariable String userId
    @GetMapping("/mapping/{userId}")
    public String mappingPath(@PathVariable("userId") String data){
        log.info("mappingPath userId={}",data);
        return "ok";
    }

    @GetMapping("/mapping/users/{userId}/orders/{orderId}")
    public String mappingPath(@PathVariable String userId,@PathVariable Long orderId){
        log.info("mappingPath userId={},orderId={}",userId,orderId);
        return "ok";
    }

    //특정 파라미터 조건 매핑. params에 해당하는 파라미터가 전송되어서 호출됨.

    /**
     * 파라미터로 추가 매핑
     * params="mode",
     * params="!mode"
     * params="mode=debug"
     * params="mode!=debug" (! = )
     * params = {"mode=debug","data=good"}
     */

    @GetMapping(value="/mapping-param",params="mode=debug")
    public String mappingParam(){
        log.info("mappingParam");
        return "ok";
    }

    //특정 헤더 조건 매핑. 파라미터 매핑과 동일하지만 HTTP 헤더를 사용한다.

    @GetMapping(value="/mapping-header",headers = "mode=debug")
    public String mappingHeader(){
        log.info("mappingHeader");
        return "ok";
    }

    //미디어 타입 조건 매핑, 타입별 처리를 다양하게 할 수 있음.
    //consumes는 오는 데이터 타입 , produces는 보내는 타입

    /**
     * Content-Type 헤더 기반 추가 매핑 Media Type
     * consumes="application/json" * consumes="!application/json"
     * consumes="application/*"
     * consumes="*\/*"
     * MediaType.APPLICATION_JSON_VALUE
     */

//    @PostMapping(value="/mapping-consume",consumes="application/json")
    @PostMapping(value="mapping-consume",consumes= MediaType.APPLICATION_JSON_VALUE)
    public String mappingConsumes(){
        log.info("mappingConsumes");
        return "ok";
    }

    @PostMapping(value="mapping-produce",produces="text/html")
    public String mappingProduces(){
        log.info("mappingProduces");
        return "ok";
    }
}
