package andy.com.basic;

public class Swap {

	public static void main(String[] args) {
		Integer a = 1;
		Integer b = 2;
		
		swap(a, b);
		System.out.println(a+","+b);
	}
	
	public static void swap(Integer a, Integer b)
	{
		Integer middle  = a;
		a = b;
		b = middle;
		System.out.println(a+","+b);
	}
}
