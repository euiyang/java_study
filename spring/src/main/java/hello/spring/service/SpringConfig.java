package hello.spring.service;

import hello.spring.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    /*private DataSource dataSource;
    private EntityManager em;*/

    private final MemberRepository memberRepository;

    @Autowired
    public SpringConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /*@Autowired
    public SpringConfig(DataSource dataSource){
        this.dataSource=dataSource;
    }*/

    @Bean
    public MemberService memberService(){
        return new MemberService(memberRepository);
    }
    /*@Bean
    public MemberRepository memberRepository(){
        return new MemoryMemberRepository();
        return new JdbcTemplateMemberRepository(dataSource);
        return new JpaMemberRepository(em);
    }*/
}
