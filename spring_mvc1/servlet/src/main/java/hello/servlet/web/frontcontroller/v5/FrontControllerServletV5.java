package hello.servlet.web.frontcontroller.v5;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v3.ControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;
import hello.servlet.web.frontcontroller.v4.controller.MemberFormControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberListControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberSaveControllerV4;
import hello.servlet.web.frontcontroller.v5.adapter.ControllerV3HandlerAdapter;
import hello.servlet.web.frontcontroller.v5.adapter.ControllerV4HandlerAdapter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name="frontControllerServletV5",urlPatterns = "/front-controller/v5/*")
public class FrontControllerServletV5 extends HttpServlet {

    //모든 객체의 최상위에는 Object 형태이므로 사용 가능한 형태이다.
    private final Map<String,Object> handlerMappingMap=new HashMap<>();

    private final List<MyHandlerAdapter> handlerAdapters=new ArrayList<>();

    public FrontControllerServletV5() {//매핑 정보 주입
        initHandlerMappingMap();
        initHandlerAdapters();
    }

    private void initHandlerAdapters() {
        handlerAdapters.add(new ControllerV3HandlerAdapter());
        handlerAdapters.add(new ControllerV4HandlerAdapter());
    }

    private void initHandlerMappingMap() {
        handlerMappingMap.put("/front-controller/v5/v3/members/new-form",new MemberFormControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members/save",new MemberSaveControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members",new MemberListControllerV3());

        handlerMappingMap.put("/front-controller/v5/v4/members/new-form",new MemberFormControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members/save",new MemberSaveControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members",new MemberListControllerV4());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //1. 핸들러 매핑 정보로 핸들러 조회
        Object handler = getHandler(request);

        //핸들러 없으면 404 오류
        if(handler==null){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        //2. 핸들러 어댑터 목록 조회 및 맞는 어댑터 가져옴
        MyHandlerAdapter adapter= getHandlerAdapter(handler);

        //3. 컨트롤러 연결 및 논리 이름 반환
        ModelView mv = adapter.handle(request, response, handler);
        String viewName = mv.getViewName();//현재 논리 이름

        //4. modelview의 view name을 viewResolver를 통과해 실제 주소 반환
        MyView view = viewResolver(viewName);

        //5. view에 랜더링
        view.render(mv.getModel(),request,response);
    }

    private MyHandlerAdapter getHandlerAdapter(Object handler) {
        //handlerAdapters 안에 있는 handler를 지원하면 해당 어댑터 반환
        for (MyHandlerAdapter adapter : handlerAdapters) {
            if(adapter.supports(handler)){
                return adapter;
            }
        }
        throw new IllegalArgumentException("hadnler adapter를 찾을 수 없습니다."+handler);
    }

    private MyView viewResolver(String viewName) {//경로 바뀔때 건드는 곳, 컨트롤러 건들지 않음.
        MyView view = new MyView("/WEB-INF/views/" + viewName + ".jsp");//이제 경로
        return view;
    }

    private Object getHandler(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        //해당 URI에 해당하는 controller로 연결
        return handlerMappingMap.get(requestURI);
    }
}
