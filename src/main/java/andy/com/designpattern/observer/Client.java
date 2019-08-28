package andy.com.designpattern.observer;

public class Client {

    public static void main(String [] args){

        WeatherStation weatherStation = new WeatherStation();
        RadioStationA radioStationA = new RadioStationA();
        RadioStationB radioStationB = new RadioStationB();

        weatherStation.registerObserver(radioStationA);
        weatherStation.registerObserver(radioStationB);

        weatherStation.dataChanged();
    }
}
