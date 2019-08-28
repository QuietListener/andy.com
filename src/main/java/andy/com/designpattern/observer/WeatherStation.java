package andy.com.designpattern.observer;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WeatherStation implements Observable<WeathInfo> {

    List<Observer<WeathInfo>> observers = new ArrayList<>();

    @Override
    public void dataChanged() {
        WeathInfo weathInfo = new WeathInfo();
        weathInfo.setWind(new Random().nextInt(1000));
        weathInfo.setTemperature(new Random().nextInt(40));

        synchronized (observers){
            for(Observer<WeathInfo> observer : observers){
                try{
                    observer.update(weathInfo);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void registerObserver(Observer<WeathInfo> o) {
        synchronized (observers){
            if(!observers.contains(o)){
                observers.add(o);
            }
        }
    }

    @Override
    public void unregisterObserver(Observer<WeathInfo> o) {
        synchronized (observers) {
            observers.remove(o);
        }
    }
}
