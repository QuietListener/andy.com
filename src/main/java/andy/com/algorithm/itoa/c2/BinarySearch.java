package andy.com.algorithm.itoa.c2;

import andy.com.algorithm.itoa.Search;

/**
 * 二分查找类
 * @author junjun
 *
 */
public class BinarySearch extends Search
{
	/**
	 *  二分查找 在s[p...q]上查找r
	 *  @param s 待查找的数组 按照升序排列
	 *  @param p 
	 *  @param q
	 *  @return 如果找到 返回找到元素的下标,否则返回-1
	 */
	public int search(int[] s, int p, int q,int r)
	{		
		while(p <=q)
		{
			int middle = (p+q)/2;              //取中间的位置进行二分
			if(r == s[middle]) return middle;  //如果找到返回middle
			else if(r<s[middle]) q = middle-1;
			else p = middle+1;
		}
		return -1;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		BinarySearch bs = new BinarySearch();
		int [] s = {0 ,1 ,1 ,2 ,3 ,3 ,4 ,5 ,8 ,9};
		for(int i = 0; i < s.length;i++)
		{
			System.out.print("s["+i+"] = "+s[i]+" ");
		}
		System.out.println("\n3 is at the position:"+bs.search(s, 0, s.length-1, 3));
		System.out.println("9 is at the position:"+bs.search(s, 0, s.length-1, 9));	
		System.out.println("11 is at the position:"+bs.search(s, 0, s.length-1, 11));	
	}
}