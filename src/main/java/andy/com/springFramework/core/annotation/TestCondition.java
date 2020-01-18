package andy.com.springFramework.core.annotation;

import org.springframework.context.annotation.*;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * 条件实例化 Bean
 */
@Configuration
public class TestCondition {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TestCondition.class);

        /**
         * 可以load java.util.Date hello1被实例化
         */
        String hello1 = context.getBean("hello1", String.class);
        System.out.println(hello1);


        String hello2 = context.getBean("hello2", String.class);
        System.out.println(hello2);
    }


    @Bean("hello1")
    @Conditional(Hello1Condition.class) //可以load java.util.Date hello1被实例化
    public String hello() {
        return "hello world1";
    }

    @Bean("hello2")
    @Conditional(Hello2Condition.class) //Hello2Condition.matches 返回 false 不会被实例化
    public String hello2() {
        return "hello world2";
    }

    public static class Hello1Condition implements Condition {

        /**
         * 如果环境中有 java.util.Date 类就返回true
         *
         * @param context
         * @param metadata
         * @return
         */
        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            try {
                context.getClassLoader().loadClass("java.util.Date");
                return true;
            } catch (Exception e) {
                return false;
            }
        }
    }

    /**
     * 返回 false
     */
    public static class Hello2Condition implements Condition {

        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            return false;
        }
    }
}
