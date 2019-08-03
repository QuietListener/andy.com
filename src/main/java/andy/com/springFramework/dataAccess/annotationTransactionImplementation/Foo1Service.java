package andy.com.springFramework.dataAccess.annotationTransactionImplementation;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;


public class Foo1Service {

    public FooServiceInterface getFooService() {
        return fooService;
    }

    public void setFooService(FooServiceInterface fooService) {
        this.fooService = fooService;
    }

    @Autowired
    private FooServiceInterface fooService;

    @Transactional
    public Foo getFoo1(String fooName) {
        System.out.println("enter getFoo1( String follName)");
        try {
            fooService.insertFoo(null);
        }catch (Exception e){
            e.printStackTrace();
            try {
                TimeUnit.SECONDS.sleep(1);
            }catch (Exception e1){
                e1.printStackTrace();
            }
        }

        System.out.println("exit getFoo1( String follName)");
        return null;
    }

    public Foo getFoo1(String fooName, String barName) {
        //throw new UnsupportedOperationException();
        return null;
    }

    public void insertFoo1(Foo foo) {
        System.out.println("enter insertFoo( Foo foo)");
        //throw new NoProductInStockException();
        System.out.println("exit insertFoo( Foo foo)");
    }

    public void updateFoo1(Foo foo) {
        //throw new UnsupportedOperationException();
    }

}