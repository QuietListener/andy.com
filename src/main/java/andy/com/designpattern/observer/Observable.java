package andy.com.designpattern.observer;

public interface Observable<T> {
     void dataChanged();
     void registerObserver(Observer<T> o);
     void unregisterObserver(Observer<T> o);
}
