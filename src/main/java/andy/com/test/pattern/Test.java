package andy.com.test.pattern;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {

	static String pattern = ".+\\+.+→.+";
	static String pattern1 = "^[a-zA-Z\']+";
	
	public static void main(String[] args) {
		
		String [] datas = {"aaaa+assdfasdf->asfasdf",
				"ab表示动作+hor发抖→又怕又恨→憎恨",
				"abs相反，变坏，离去+tract拉+ion名词后缀 → 抽象，提取，抽出，分离",
				"abs相反，变坏，离去tract拉ion名词后缀 → 抽象，提取，抽出，分离",
				"aaaa+assdfasdf -> asfa -> sdf",
				"aaaa",
				};
		
		for(String data:datas)
		{
			System.out.println(Pattern.matches(pattern, data)+"  :"+data);
		}
		

		Pattern p = Pattern.compile(pattern1);
		System.out.println();
		for(String data:datas)
		{
			Matcher m = p.matcher(data);
			System.out.print(data+"   :    ");
			while(m.find())
			{
				System.out.print(data.substring(m.start(), m.end()) +" ; ");
			}
			
			System.out.println();
		}
		
	}

}
