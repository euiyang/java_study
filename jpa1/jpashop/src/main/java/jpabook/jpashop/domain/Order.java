package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="orders")
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column(name="order_id")
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @OneToMany(mappedBy="order",cascade=CascadeType.ALL)
    //default=LAZY
    //cascadeType.ALL= cascade 전파
    private List<OrderItem> orderItems=new ArrayList<>();

    @OneToOne(fetch=FetchType.LAZY,cascade=CascadeType.ALL)
    @JoinColumn(name="delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    //연관 관계 메소드
    public void setMember(Member member){
        this.member=member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem){
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery){
        this.delivery=delivery;
        delivery.setOrder(this);
    }

    //생성 메소드
    public static Order createOrder(Member member,Delivery delivery,OrderItem... orderItems){
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for(OrderItem orderItem:orderItems){
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }


    //비즈니스 로직
    public void cancel(){
        if(delivery.getStatus()==DeliveryStatus.COMP){
            throw new IllegalStateException("배송완료된 상품은 취소가 불가능");
        }

        this.setStatus(OrderStatus.CANCEL);
        for(OrderItem orderItem: orderItems){
            orderItem.cancel();
        }
    }

    //조회 로직
    public int getTotalPrice(){
        int totalPrice=0;
        for(OrderItem orderItem:orderItems){
            totalPrice+=orderItem.getTotalPrice();
        }
        return totalPrice;
    }
}
