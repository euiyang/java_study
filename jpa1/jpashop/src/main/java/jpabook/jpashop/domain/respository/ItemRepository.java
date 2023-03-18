package jpabook.jpashop.domain.respository;

import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item){
        if(item.getId()==null){//db에 신규 저장
            em.persist(item);

        }else{//db에 이미 존재하는 것에 저장
            //merge는 모든 변수에 대해 이를 실행하고 영속성 컨텍스트에서 관리하는 객체를 새로 반환
            //만약 merge할 값이 없으면 null로 업데이트할 위험 존재재
           em.merge(item);

        }
    }

    public Item findOne(Long id){
        return em.find(Item.class,id);
    }

    public List<Item> findAll(){
        return em.createQuery("select i from Item i",Item.class)
                .getResultList();
    }
}
