package andy.com.test.pattern;


public class TestLongOverflow {

	static String pattern = ".+\\+.+→.+";
	
	public static void main(String[] args) {
		
		long l = Long.MAX_VALUE;
		int [] plus = {1,2,3,4,5,6,7,8};
		System.out.println("max long = "+ l+"; min long = "+Long.MIN_VALUE);
		for(int p : plus)
			System.out.println((p+l)+"");
		
		System.out.println(l*2);
		
	}

}
