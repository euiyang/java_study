package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SingletonTest {

    @Test
    @DisplayName("스프링 없는 순수한 DI 컨테이너")
    void pureContainer(){
        AppConfig appConfig=new AppConfig();

        //호출마다 객체 생성하는 경우
        MemberService memberService1=appConfig.memberService();
        MemberService memberService2=appConfig.memberService();

        //참조값이 다른 것을 확인
        System.out.println("memberservice1 =" + memberService1);
        System.out.println("memberservice2 ="+ memberService2);

        Assertions.assertThat(memberService1).isNotSameAs(memberService2);
        //요청마다 새로운 객체 생성-> 메모리 낭비 심함.
        //1개의 객체만 생성되게 하면 된다.-> 싱글톤 패턴

    }
    
    @Test
    @DisplayName("싱글톤 패턴을 적용한 객체 사용")
    void singletonServiceTest(){
        SingletonService instance1 = SingletonService.getInstance();
        SingletonService instance2 = SingletonService.getInstance();

        System.out.println("instance1 =" + instance1);
        System.out.println("instance2 ="+ instance2);

        Assertions.assertThat(instance1).isSameAs(instance2);
    }

    @Test
    @DisplayName("스프링 컨테이너와 싱글톤")
    void SpringContainer(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);


        MemberService memberService1=ac.getBean("memberSerivce",MemberService.class);
        MemberService memberService2=ac.getBean("memberSerivce",MemberService.class);

        System.out.println("memberservice1 =" + memberService1);
        System.out.println("memberservice2 ="+ memberService2);

        Assertions.assertThat(memberService1).isNotSameAs(memberService2);
    }
}
