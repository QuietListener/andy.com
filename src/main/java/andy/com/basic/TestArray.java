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


		System.out.println("========2018-9-17========");

		List<Integer> a1 = new ArrayList<>(Arrays.asList(0,1,2));
		List<Integer> a2 =  new ArrayList<>(Arrays.asList(2,3,4));
		List<Integer> a1_clone = new ArrayList<>(a1);
		List<Integer> a2_clone = new ArrayList<>(a2);

		a1_clone.retainAll(a2);
		a1.removeAll(a1_clone);
		a2.removeAll(a2_clone);

		System.out.println(a1);
		System.out.println(a2);


		List<Integer> a = Arrays.asList(1,2);
        List<Integer> b = Arrays.asList(1,2);
        List<Integer> c = Arrays.asList(1,2,3);

        System.out.println("a == b:"+a.equals(b));
        System.out.println("a == c:"+a.equals(c));




		List<Integer> aabb = new ArrayList<>(Arrays.asList(1,2));
		aabb.add(0,-1);
		System.out.println(aabb);

		System.out.println(aabb.subList(1,aabb.size()));


	}

}
