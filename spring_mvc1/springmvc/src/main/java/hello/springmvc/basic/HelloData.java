package hello.springmvc.basic;


import lombok.Data;

@Data//@Getter,@Setter,@ToString,@EqualsAnsHashCode,@RequiredArgsConstructor 적용
public class HelloData {
    private String username;
    private int age;
}
