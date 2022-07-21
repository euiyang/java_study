package hello.servlet.web.frontcontroller.v3;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v2.ControllerV2;
import hello.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name="frontControllerServletV3",urlPatterns = "/front-controller/v3/*")
public class FrontControllerServletV3 extends HttpServlet {
    private Map<String, ControllerV3> controllerMap=new HashMap<>();

    public FrontControllerServletV3() {//매핑 정보 주입
        controllerMap.put("/front-controller/v3/members/new-form",new MemberFormControllerV3());
        controllerMap.put("/front-controller/v3/members/save",new MemberSaveControllerV3());
        controllerMap.put("/front-controller/v3/members",new MemberListControllerV3());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("FrontControllerServletV3.service");

        String requestURI = request.getRequestURI();

        //해당 URI에 해당하는 controller로 연결
        ControllerV3 controller = controllerMap.get(requestURI);

        if(controller==null){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        //해당 controller로 보냄
        Map<String, String> paramMap = createParamMap(request);
        ModelView mv= controller.process(paramMap);

        String viewName = mv.getViewName();//현재 논리 이름

        MyView view = viewResolver(viewName);

        view.render(mv.getModel(),request,response);

    }

    private MyView viewResolver(String viewName) {//경로 바뀔때 건드는 곳, 컨트롤러 건들지 않음.
        MyView view = new MyView("/WEB-INF/views/" + viewName + ".jsp");//이제 경로
        return view;
    }

    private Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String,String> paramMap=new HashMap<>();
        request.getParameterNames().asIterator()
                .forEachRemaining(paramName->paramMap.put(paramName, request.getParameter(paramName)));
        return paramMap;
    }
}
