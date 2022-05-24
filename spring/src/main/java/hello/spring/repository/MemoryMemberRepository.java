package hello.spring.repository;

import hello.spring.domain.Member;

import java.util.*;

public class MemoryMemberRepository implements MemberRepository{

    private static Map<Long, Member> store=new HashMap<>();
    private static long sequence=0L;

    @Override
    public Member save(Member member) {
        member.setId(++sequence);//id 세팅
        store.put(member.getId(),member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
        //null 반환 가능성 있는 것은 Optional.ofNullable로 감싸준다.
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(member->member.getName().equals(name))
                .findAny();
    }

    @Override
    public List<Member> findAll() {//보통 리스트로 반환하면 사용하기 편함
        return new ArrayList<>(store.values());
    }

    public void clearStore(){
        store.clear();
    }
}
