package andy.com.annotation.test1;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)//Retention注解决定MyAnnotation注解的生命周期
@Target( { ElementType.METHOD })
public @interface MyTestAnnotation1 {

}
