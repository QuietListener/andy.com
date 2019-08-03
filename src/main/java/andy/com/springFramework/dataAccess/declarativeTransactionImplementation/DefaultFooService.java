package andy.com.springFramework.dataAccess.declarativeTransactionImplementation;

public class DefaultFooService implements FooService {

    public Foo getFoo(String fooName) {
        //throw new UnsupportedOperationException();
        System.out.println("enter getFoo( String follName)");
        insertFoo(null);
        System.out.println("exit getFoo( String follName)");
        return null;
    }

    public Foo getFoo(String fooName, String barName) {
        //throw new UnsupportedOperationException();
        return null;
    }

    public void insertFoo(Foo foo) {
        System.out.println("enter insertFoo( Foo foo)");
        //throw new NoProductInStockException();
        System.out.println("exit insertFoo( Foo foo)");
    }

    public void updateFoo(Foo foo) {
        //throw new UnsupportedOperationException();
    }

}