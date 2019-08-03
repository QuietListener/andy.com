package andy.com.springFramework.dataAccess.codeTransactionImplementation.declarativeTransactionImplementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

public class DefaultFooService implements FooService {

    @Autowired
    DataSourceTransactionManager txManager;

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