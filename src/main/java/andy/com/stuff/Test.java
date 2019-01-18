package andy.com.stuff;

public class Test {
    static public void main(String [] args){
        boolean ret = "can't stand".matches(".*[^a-zA-Z.'\\s_\\-].*");
        System.out.println(ret);
    }
}
