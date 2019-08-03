package andy.com.springFramework.dataAccess.annotationTransactionImplementation;

import org.springframework.aop.framework.AopContext;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

public class FooService implements FooServiceInterface{

    public Foo getFoo(String fooName) {
        System.out.println("enter getFoo");
        //this.insertFoo(null);
        ((FooServiceInterface)AopContext.currentProxy()).insertFoo(null);
        System.out.println("exit getFoo");
        return null;
    }


    @Transactional(propagation = Propagation.NESTED,rollbackFor = Exception.class)
    public void insertFoo(Foo foo) {
        System.out.println("enter insertFoo( Foo foo)");

        System.out.println("exit insertFoo( Foo foo)");
        //throw new RuntimeException("ddd");
    }

    @Transactional(propagation = Propagation.NESTED)
    public Foo getFoo(String fooName, String barName) {

        throw new UnsupportedOperationException();
    }


    @Transactional(propagation = Propagation.REQUIRED, timeout = 1)
    public void updateFoo(Foo foo) {

        System.out.println("updateFoo");
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //throw new UnsupportedOperationException();
    }

}