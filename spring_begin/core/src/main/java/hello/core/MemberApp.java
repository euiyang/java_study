package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MemberApp {
    public static void main(String[] args) {
        //MemberService memberService =new MemberServiceImpl(memberRepository);
//        AppConfig appConfig=new AppConfig();
//        MemberService memberService= appConfig.memberService();

        ApplicationContext applicationContext=new AnnotationConfigApplicationContext(AppConfig.class);
        //Appconfig 내부 @Bean붙은 것을 스프링 컨테이너에서 관리해줌.
        MemberService memberService=applicationContext.getBean("memberService",MemberService.class);

        Member member=new Member(1L,"memberA", Grade.VIP);
        memberService.join(member);

        Member findMember=memberService.findMember(1L);
        System.out.println("new Member= "+member.getName());
        System.out.println("find Member= "+findMember.getName());
    }
}
