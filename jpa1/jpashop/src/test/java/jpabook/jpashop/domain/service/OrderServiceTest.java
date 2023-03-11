package jpabook.jpashop.domain.service;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.domain.respository.OrderRepository;
import jpabook.jpashop.exception.NotEnoughStockException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.fail;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    EntityManager em;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;


    @Test
    public void ordItem() throws Exception{

        Member member = createMember();

        Book book = createBook("시골 jpa", 10000, 10);
        int orderCount=2;

        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        Order getOrder = orderRepository.findOne(orderId);
        Assertions.assertEquals(OrderStatus.ORDER,getOrder.getStatus());
        Assertions.assertEquals(1,getOrder.getOrderItems().size());
        Assertions.assertEquals(10000*orderCount,getOrder.getTotalPrice());
        Assertions.assertEquals(8,book.getStockQuantity());

    }

    private Book createBook(String name, int price, int stockQuantity) {
        Book book=new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        Member member=new Member();
        member.setName("dd1");
        member.setAddress(new Address("서울","경기","123123"));
        em.persist(member);
        return member;
    }

    @Test
    public void exceed() throws Exception{
        Member member = createMember();
        Item item = createBook("시골 jpa",10000,10);

        int orderCount=11;

        Assertions.assertThrows(NotEnoughStockException.class,()->
                orderService.order(member.getId(),item.getId(),orderCount)
                );
        fail("예외 발생");

    }

    @Test
    public void cancelOrder() throws Exception{
        Member member = createMember();
        Book item = createBook("country", 10000, 10);

        int orderCount=2;

        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        orderService.cancelOrder(orderId);

        Order getOrder = orderRepository.findOne(orderId);

        assertEquals("주문 취소시 상태는 cancel",OrderStatus.CANCEL,getOrder.getStatus());
        assertEquals("주문 취소 상품은 양 증가", 10,item.getStockQuantity());
    }


}