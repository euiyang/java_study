package jpabook.jpashop.domain.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.respository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
//데이터 변경이 있는 곳은 무조건 transaction이 필요하다.
//readOnly가 true이면 데이터 변경이 없는 곳에서 리소스를 적게 먹는다.
public class MemberService {

//    @Autowired
//    private MemberRepository memberRepository;
    //변경 불가능이라는 단점

    private final MemberRepository memberRepository;

    @Autowired//생성자가 1개만 있는 경우 Autowired 어노테이션이 없어도 자동 주입
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }//생성자 주입, 아래의 annotation 사용시 생성자 따로 생성 안해도 됨.
    // @AllArgsConstructor= 생성자 자동 생성
    // @RequiredArgsConstructor= final 필드 생성자 자동 생성

    @Transactional
    public Long join(Member member){
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }
}
