package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="order_item")
@Getter
@Setter
public class OrderItem {


    @Id @GeneratedValue
    @Column(name="order_item_id")
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="item_id")
    private Item item;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="order_id")
    private Order order;

    private int orderPrice;
    private int count;

    protected OrderItem(){
        //해당 생성자를 사용하지 못하게 하기 위해
        //또는 클래스 레벨 어노테이션에 NoArgsConstructor(access=accessLevel.PROTECTED) 추가
    }


    //생성 메소드
    public static OrderItem createOrderItem(Item item,int orderPrice,int count){
        OrderItem orderItem=new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count);
        return orderItem;
    }

    //비즈니스 로직

    public void cancel(){
        getItem().addStock(count);
    }

    public int getTotalPrice(){
        return getOrderPrice()*getCount();
    }
}
