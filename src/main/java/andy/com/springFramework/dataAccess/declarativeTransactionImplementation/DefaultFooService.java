package andy.com.springFramework.dataAccess.declarativeTransactionImplementation;

public class DefaultFooService implements FooService {

    public Foo getFoo(String fooName) {
        throw new UnsupportedOperationException();
    }

    public Foo getFoo(String fooName, String barName) {
        throw new UnsupportedOperationException();
    }

    public void insertFoo(Foo foo) {
        throw new NoProductInStockException();
    }

    public void updateFoo(Foo foo) {
        throw new UnsupportedOperationException();
    }

}