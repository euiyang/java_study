package hello.spring.repository.service;

import hello.spring.domain.Member;
import hello.spring.repository.MemberRepository;
import hello.spring.repository.MemoryMemberRepository;

import java.util.List;
import java.util.Optional;

public class MemberService {

    private final MemberRepository memberRepository=new MemoryMemberRepository();

    public Long join(Member member){
        //같은 이름의 중복 회원은 안됨이라는 조건

        Optional<Member> result = memberRepository.findByName(member.getName());
        result.ifPresent(m->{//Optional로 감싸면 다음과 같은 함수를 사용가능. if null X
            throw new IllegalStateException("이미 존재합니다");
        });
       /*위와 동일
        memberRepository.findByName(member.getName())//findByName 자체가 Optional임
                .ifPresent(m->{//Optional로 감싸면 다음과 같은 함수를 사용가능. if null X
                    throw new IllegalStateException("이미 존재합니다");
                });
        */
        //validateDuplicateMember(member);

        memberRepository.save(member);
        return member.getId();
    }
    private void validateDuplicateMember(Member member){
        memberRepository.findByName(member.getName())//findByName 자체가 Optional임
                .ifPresent(m->{//Optional로 감싸면 다음과 같은 함수를 사용가능. if null X
                    throw new IllegalStateException("이미 존재합니다");
                });
    }

    //회원 조회

    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberid){
        return memberRepository.findById(memberid);
    }
}
