package jpabook.jpashop.domain.respository;

import jpabook.jpashop.domain.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderSearch {
    private String memberName;
    private OrderStatus orderStatus;
}
