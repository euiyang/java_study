package hello.spring.repository.service;

import hello.spring.domain.Member;
import hello.spring.repository.MemberRepository;
import hello.spring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach
    public void beforeEach(){
        memberRepository=new MemoryMemberRepository();
        memberService=new MemberService();
    }
    @AfterEach
    public void aftereach(){
        memberRepository.clearStore();
    }
    @Test
    void join() {
        //given
        Member member=new Member();
        member.setName("hello");
        //when
        Long saveId=memberService.join(member);

        //then
        Member findMember=memberService.findOne(saveId).get();
        Assertions.assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void 중복회원선택(){
        //given
        Member member1 =new Member();
        member1.setName("spring1");

        Member member2 =new Member();
        member2.setName("spring1");

        //when
        memberService.join(member1);
        IllegalStateException e=assertThrows(IllegalStateException.class, ()->memberService.join(member2));

        Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재합니다");
        /*
        try{
            memberService.join(member2);
            fail();
        }catch(IllegalStateException e){
            Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재합니다");
        }
         */
        //then
    }
    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}