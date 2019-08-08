package andy.com.springFramework.core.basic;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;

/**
 * 1.Bean自身方法: init-method和destory-method方法
 * 2.Bean级生命周期接口方法:  BeanFactoryAware, BeanNameAware, InitializingBean, DisposableBean
 * 3.容器级别生命周期接口方法: InstantiationAwareBeanPostProcessor,BeanPostProcessor 都是"后处理器"
 * 4. 工厂(BeanFactory)后处理接口方法:
 */
class Person implements BeanFactoryAware, BeanNameAware, InitializingBean, DisposableBean {

    private String name;
    private String address;
    private String phone;

    private BeanFactory beanFactory;
    private String beanName;

    public Person() {
        System.out.println("**【构造器】调用Person的构造器实例化");
    }

    // 通过<bean>的init-method属性指定的初始化方法
    public void myInit() {
        System.out.println("**【init-method】调用<bean>的init-method属性指定的初始化方法");
    }

    // 通过<bean>的destroy-method属性指定的初始化方法
    public void myDestory() {
        System.out.println("**【destroy-method】调用<bean>的destroy-method属性指定的初始化方法");
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {

        System.out.println("**【BeanFactoryAware接口】调用BeanFactoryAware.setBeanFactory()");
        this.beanFactory = beanFactory;
    }

    @Override
    public void setBeanName(String s) {
        System.out.println("**【BeanNameAware接口】调用BeanNameAware.setBeanName()");
        this.beanName = s;
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("**【DiposibleBean接口】调用DiposibleBean.destory()");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("**【InitializingBean接口】调用InitializingBean.afterPropertiesSet()");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        System.out.println("**【注入属性】注入属性name");
        this.name = name;
    }

    public String getAddress() {
        System.out.println("**【注入属性】注入属性address");
        return address;
    }

    public void setAddress(String address) {
        System.out.println("**【注入属性】注入属性address");
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        System.out.println("**【注入属性】注入属性phone");
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phone=" + phone +
                '}';
    }
}