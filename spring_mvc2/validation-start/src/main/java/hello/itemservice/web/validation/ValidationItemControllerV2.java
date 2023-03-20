package hello.itemservice.web.validation;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/validation/v2/items")
@RequiredArgsConstructor
public class ValidationItemControllerV2 {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "validation/v2/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v2/item";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item());
        //검증에 실패시 재사용 가능하기 때문에 빈 객체를 넘기기도 한다.
        return "validation/v2/addForm";
    }

    @PostMapping("/add")
    //BindingResult는 binding하는 객체 바로 다음 파라미터에 위치해 있어야한다(아래는 item의 값을 binding)
    public String addItem(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

        //검증 오류 결과 보관
        Map<String, String> errors = new HashMap<>();

        //검증 로직
        if (!StringUtils.hasText(item.getItemName())) {
//            errors.put("itemName", "상품 이름은 필수입니다");
            //FieldError는 오류 발생시 사용자 입력 값을 저장하는 기능 제공
            bindingResult.addError(new FieldError("item","itemName","상품이름은 필수 입니다."));
            //rejected value를 남겨서 넘겨줌
            bindingResult.addError(new FieldError("item","itemName",item.getItemName(),false,null,null,"상품이름은 필수 입니다."));

        }
        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
//            errors.put("price", "가격은 1,000~1,000,000 까지 가능");
            bindingResult.addError(new FieldError("item","price","가격은 1,000~1,000,000 까지 가능"));
            bindingResult.addError(new FieldError("item","price",item.getPrice(),false,null,null,"가격은 1,000~1,000,000 까지 가능"));
        }
        if (item.getQuantity() == null || item.getQuantity() >= 9999) {
//            errors.put("quantity", "수량은 최대 9,999 까지 허용");
            bindingResult.addError(new FieldError("item","price","수량은 최대 9,999 까지 허용"));
            bindingResult.addError(new FieldError("item","price",item.getQuantity(),false,null,null,"수량은 최대 9,999 까지 허용"));
        }

        //복합 룰 검증
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
//                errors.put("globalError", "가격* 수량의 값은 10,000원 이상이어야한다. 현재값=" + resultPrice);
                bindingResult.addError(new ObjectError("item","가격* 수량의 값은 10,000원 이상이어야한다. 현재값=" + resultPrice));
            }
        }

        //검증 실패시 다시 입력 폼으로
//        if (!errors.isEmpty()) { //부정의 부정형은 한번에 코드를 읽기 힘드므로 수정하면 좋다.
//            model.addAttribute("errors", errors);
//            return "validation/v2/addForm";
//        }

        if(bindingResult.hasErrors()){
            log.info("errors={}",errors);
//            model.addAttribute("errors",bindingResult); // bindingResult는 자동으로 view로 넘어가므로 넘길 필요 없음.
            return "validation/v2/addForm";
        }

        //성공 로직
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v2/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/validation/v2/items/{itemId}";
    }
}