package andy.com.test.pattern;

import java.util.regex.Pattern;

public class Test1 {

	static String pattern = ".+\\+.+→.+";
	
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
	
		String s = "abs相反，变坏，离去+tract拉+ion名词后缀";
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < s.length(); i++)
		{
			String c = s.substring(i, i+1);
			if (Pattern.matches("[a-zA-z]",c))
			{
				sb.append(c);
			}
		}
		
		System.out.println(sb.toString());
	}

}
