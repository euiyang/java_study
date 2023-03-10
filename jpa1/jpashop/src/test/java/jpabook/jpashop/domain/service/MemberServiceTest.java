package jpabook.jpashop.domain.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.respository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

//@RunWith(SpringRunner.class) // junit4에서 테스트 진행시
@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    //@Transactional은 기본적으로 끝나면 rollback을 한다(실제 db에 안 들어감)
    // @Rollback(false)를 하던가
    // 아니면 영속성 컨텍스트에 있는 쿼리를 데이터 베이스에 반영하기 위해
    // EntityManager를 생성 후 persist() 이후에 em.flush()를 적용한다.
    public void join() throws Exception{
        //given
        Member member = new Member();
        member.setName("kim");

        Long savedId = memberService.join(member);

        Assertions.assertEquals(member,memberRepository.findOne(savedId));
    }

//    @Test
//    public void dup_exc() throws Exception{
//        Member member1 = new Member();
//        member1.setName("kim");
//
//        Member member2 = new Member();
//        member2.setName("kim");
//
//        memberService.join(member1);
//        try{
//            memberService.join(member2);//예외 발생 상황
//        }catch(IllegalStateException e){
//            return;
//        }
//        Assertions.fail("예외 발생");
//    }

    //junit4
//    @Test(expected= IllegalStateException.class)
//    public void dup_exc() throws Exception{
//        Member member1 = new Member();
//        member1.setName("kim");
//
//        Member member2 = new Member();
//        member2.setName("kim");
//
//        memberService.join(member1);
//        memberService.join(member2);//예외 발생 상황

//        Assertions.fail("예외 발생");
//    }

    @Test
    public void dup_exc() throws Exception{
        //given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        //when
        memberService.join(member1);

        //then
        Assertions.assertThrows(IllegalStateException.class,()->
            memberService.join(member2)
        );
    }
}