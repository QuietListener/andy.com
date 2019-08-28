package andy.com.designpattern.observer;

public class RadioStationA implements Observer<WeathInfo> {

    @Override
    public void update(WeathInfo o) {
        System.out.println("中央人民广播电台, 当前温度"+o.getTemperature()+"，风速是:"+o.getWind());
    }
}
