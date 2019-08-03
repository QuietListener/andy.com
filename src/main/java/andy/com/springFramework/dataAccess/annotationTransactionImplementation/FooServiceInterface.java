package andy.com.springFramework.dataAccess.annotationTransactionImplementation;

public interface FooServiceInterface {

    public Foo getFoo(String fooName);
    public void insertFoo(Foo foo);
    public Foo getFoo(String fooName, String barName);
    public void updateFoo(Foo foo);

}
