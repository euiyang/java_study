package hello.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.annotation.Target;

@Controller
public class HelloController {
    @GetMapping("hello")// 도메인/hello 입력시 연결되는 지점
    public String hello(Model model){
        model.addAttribute("data","hello!!");
        return "hello";//templates의 hello.html을 찾아서 랜더링 해라
    }

    @GetMapping("hello-mvc")//localhost:8080/hello-mvc?name=spring으로 인자 전달
    public String helloMvc(@RequestParam("name") String name, Model model){
        model.addAttribute("name",name);
        return "hello-template";
    }
    @GetMapping("hello-string")
    @ResponseBody//view가 없고 문자 그대로 내려감.
    //@ResponseBody: 문자열이면 그대로(String converter), 객체면 json형식으로 출력(json converter)
    public String helloString(@RequestParam("name") String name){
        return "hello"+name;
    }
    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name){
        Hello hello=new Hello();
        hello.setName(name);
        return hello;
    } //json 형식으로 반환됨. key-value쌍
    static class Hello{
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
