package andy.com.basic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class TestArray {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<String> list = new ArrayList<>(Arrays.asList("1","1"));
		System.out.println(list.toString());
		System.out.println(StringUtils.isBlank(null));
	}

}
