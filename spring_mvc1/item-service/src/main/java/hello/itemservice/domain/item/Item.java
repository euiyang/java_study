package hello.itemservice.domain.item;

import lombok.Data;
import lombok.Getter;

@Data// 위험. 필요한 anntation만 추가하는 게 좋음.

@Getter

public class Item {
    private Long id;
    private String itemName;
    //Integer 사용이유: null 입력 가능성 때문
    private Integer price;
    private Integer quantity;

    public Item() {
    }

    public Item( String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
