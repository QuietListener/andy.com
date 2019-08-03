package andy.com.springFramework.dataAccess.declarativeTransactionImplementation;
public interface Foo1Service {

    Foo getFoo1(String fooName);

    Foo getFoo1(String fooName, String barName);

    void insertFoo1(Foo foo);

    void updateFoo1(Foo foo);

}