package hello.spring.repository;

import hello.spring.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);
    Optional<Member> findById(Long id);//null이 반환될 때 단순 null 반환하지 않고 oprional이라는 형태로 반환
    Optional<Member> findByName(String name);
    List<Member> findAll();

}
