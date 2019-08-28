package andy.com.designpattern.observer;

public class RadioStationB implements Observer<WeathInfo> {

    @Override
    public void update(WeathInfo o) {
        System.out.println("四川人民广播电台, 当前温度"+o.getTemperature()+"，风速是:"+o.getWind());
    }
}
