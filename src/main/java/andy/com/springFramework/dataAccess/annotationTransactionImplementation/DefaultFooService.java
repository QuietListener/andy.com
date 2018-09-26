package andy.com.springFramework.dataAccess.annotationTransactionImplementation;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Transactional(readOnly = true)
public class DefaultFooService implements FooService {

    public Foo getFoo(String fooName) {

        System.out.println("getFoo");
        throw new UnsupportedOperationException();
    }

    public Foo getFoo(String fooName, String barName) {

        throw new UnsupportedOperationException();
    }

    public void insertFoo(Foo foo) {

        System.out.println("insertFoo");
        throw new NoProductInStockException();
    }

    // these settings have precedence for this method
    @Transactional(propagation = Propagation.REQUIRED,timeout = 1)
    public void updateFoo(Foo foo) {

        System.out.println("updateFoo");
        try {
            TimeUnit.SECONDS.sleep(10);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        //throw new UnsupportedOperationException();
    }

}