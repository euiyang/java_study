package hello.spring.repository;

import hello.spring.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class MemoryMemberRepositoryTest {
    MemoryMemberRepository repository=new MemoryMemberRepository();

    //테스트 케이스는 순서가 무작위로 선정되므로 순서에 의존적이지 않게 만든다.

    @AfterEach//하나의 테스트 끝날때마다 데이터 삭제.
    public void afterEach(){
        repository.clearStore();
    }

    @Test
    public void save(){
        Member member=new Member();
        member.setName("spring");

        repository.save(member);
        Member result=repository.findById(member.getId()).get();

        //Assertions.assertEquals(member,result);
        Assertions.assertThat(member).isEqualTo(result);
    }

    @Test
    public void findByName(){
        Member member1=new Member();
        member1.setName("spring1");
        repository.save(member1);
        Member member2=new Member();
        member2.setName("spring2");
        repository.save(member2);

        Member result=repository.findByName("spring1").get();

        Assertions.assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll(){
        Member member1=new Member();
        member1.setName("spring1");
        repository.save(member1);
        Member member2=new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result=repository.findAll();
        Assertions.assertThat(result.size()).isEqualTo(2);
    }
}
