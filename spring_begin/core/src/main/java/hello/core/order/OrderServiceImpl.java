package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceImpl implements OrderService{

//    private final MemberRepository memberRepository=new MemoryMemberRepository();
//    private final DiscountPolicy discountPolicy=new FixDiscountPolicy();
    //고치면 클라이언트를 고치게 된다-> OCP 위반
    //discountPolicy 뿐만 아니라 FixDiscountPolicy도 의존
    //인터페이스, 구체화 전부 의존-> DIP 위반

    private final MemberRepository memberRepository;//final이면 생성자로 할당 되어야함.
    private final DiscountPolicy discountPolicy;
    //->인터페이스에만 의존. 하지만 다른 방법으로 객체를 생성하고 주입해야 한다.

    @Autowired
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member=memberRepository.findById(memberId);
        int discountPrice= discountPolicy.discount(member,itemPrice);
        return new Order(memberId,itemName,itemPrice,discountPrice);
    }

    public MemberRepository getMemberRepository(){
        return memberRepository;
    }
}
