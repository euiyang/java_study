package hello.core.beanfind;

import hello.core.AppConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

public class ApplicationContextInfoTest {

    AnnotationConfigApplicationContext ac=new AnnotationConfigApplicationContext(AppConfig.class);
    @Test
    @DisplayName("모든 빈 출력하기")
    void findAllBean(){
        String[] beanDefinitionNames=ac.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            Object bean = ac.getBean(beanDefinitionName);
            System.out.println("name ="+beanDefinitionName+" object="+bean);
        }
    }
    @Test
    @DisplayName("에플리케이션 빈 출력하기")
    void findApplicationBean(){
        String[] beanDefinitionNames=ac.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            BeanDefinition beanDefinition = ac.getBeanDefinition(beanDefinitionName);
            //bean에 대한 메타 정보
            if(beanDefinition.getRole()==beanDefinition.ROLE_APPLICATION){
                //ROLE_APPLICATION -> 내가 개발하기 위해 등록한 빈일 경우 출력
                Object bean = ac.getBean(beanDefinitionName);
                System.out.println("name ="+beanDefinitionName+" object="+bean);
            }

        }
    }
}
