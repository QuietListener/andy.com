package andy.com.basic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class TestArray {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<String> list = new ArrayList<>(Arrays.asList("1","1"));
		System.out.println(list.toString());
		System.out.println(StringUtils.isBlank(null));
		List<ArrayList<String>> l1 = new ArrayList<ArrayList<String>>();
		List<Integer> l2 = new ArrayList<Integer>();
		System.out.println(l1.getClass());
		System.out.println(l1.getClass().isInstance(l2));
		
		Gson gson = new Gson();
		List<ArrayList<String>> l3 = gson.fromJson("[[\"en-h[a]nce\",\"f[a]n-cy\"]]", new TypeToken<ArrayList<ArrayList<String>>>(){}.getType());
		System.out.println(l3);
		
		Pattern pattern = Pattern.compile("\\[[\\w|\\d]*\\]");
		String strs =  "aban [d] dd []|| [aa] [asdfa] on";
		Matcher m = pattern.matcher(strs);
		int count = 0;
		while(m.find()){count++;}
		System.out.println(count);
	}

}
