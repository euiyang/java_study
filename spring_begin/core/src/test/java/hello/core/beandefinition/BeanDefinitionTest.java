package hello.core.beandefinition;

import hello.core.AppConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.test.context.support.GenericXmlContextLoader;

import java.lang.annotation.Annotation;

public class BeanDefinitionTest {
//    AnnotationConfigApplicationContext ac=new AnnotationConfigApplicationContext(AppConfig.class);

    GenericXmlApplicationContext ac=new GenericXmlApplicationContext("appConfig.xml");

    @Test
    @DisplayName("빈 설정 메타저보 확인")
    void findApplicationBean(){
        String[] beanDefinitionNames=ac.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            BeanDefinition beanDefinition=ac.getBeanDefinition(beanDefinitionName);
            if(beanDefinition.getRole()==beanDefinition.ROLE_APPLICATION){
                System.out.println("beanDefinitionName = "+beanDefinitionName+
                        "beandefinition = "+beanDefinition);
            }
        }
    }
}