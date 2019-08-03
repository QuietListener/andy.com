package andy.com.springFramework.dataAccess.declarativeTransactionImplementation;

import org.springframework.beans.factory.annotation.Autowired;

public class DefaultFoo1Service implements Foo1Service {

    public FooService getFooService() {
        return fooService;
    }

    public void setFooService(FooService fooService) {
        this.fooService = fooService;
    }

    @Autowired
    private FooService fooService;

    public Foo getFoo1(String fooName) {
        //throw new UnsupportedOperationException();
        System.out.println("enter getFoo1( String follName)");
        fooService.insertFoo(null);
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