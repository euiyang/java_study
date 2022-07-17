package hello.servlet.basic.request;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

 // 예시 주소: http://localhost:8080/request-param?username=hello&username=kim&age=20

@WebServlet(name="requestParamServlet",urlPatterns = "/request-param")
public class RequestParamServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //reques.getparameter는 get Method 뿐만 아니라 Post 형식의 조회도 가능

        Enumeration<String> parameterNames = request.getParameterNames();

        System.out.println("전체 파라미터 조회");

        parameterNames.asIterator()
                .forEachRemaining(paramName-> System.out.println(paramName+" = "+request.getParameter(paramName)));
        //파라미터 명들을 이터레이터로 순환하면서 해당 파라미터 명으로 하나씩 출력


        System.out.println("단일 파라미터 조회");

        String age = request.getParameter("age");
        String username = request.getParameter("username");
        System.out.println("age = " + age);
        System.out.println("username = " + username);


        System.out.println("이름이 같은 복수 파라미터 조회");
        //ex)username="k",username="h"일 경우 값이 두 개가 존재하므로 getparameterValues로 꺼낸다.

        String[] usernames = request.getParameterValues("username");
        for(String name:usernames){
            System.out.println("username = " +name);
        }


    }
}
