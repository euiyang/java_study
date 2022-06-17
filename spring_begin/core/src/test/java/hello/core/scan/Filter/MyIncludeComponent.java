package hello.core.scan.Filter;

import java.lang.annotation.*;

@Target(ElementType.TYPE)//TYPE=클래스 레벨에 붙음
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyIncludeComponent {
}
