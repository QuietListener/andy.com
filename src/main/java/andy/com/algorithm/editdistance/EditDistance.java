package andy.com.algorithm.editdistance;

import java.util.Arrays;
import java.util.Collections;

/**
 * a到b的编辑距离是将a转换为b的最少操作次数，只能有如下操作：
 * 插入一个字符：ab -> abj
 * 删除一个字符：abj -> aj
 * 替换一个字符：axj -> ayj
 * <p>
 * 递归是从后往前的计算，
 * 动态规划是从前往后的计算
 */
public class EditDistance {


    /**
     * 递归来解决
     *
     * @param a
     * @param b
     * @param i
     * @param j
     * @return
     */
    public int editDistance1(char[] a, char[] b, int i, int j) {
        if (i == 0 || i == -1) {
            return j+1;
        } else if (j == 0 || j == -1) {
            return i+1;
        } else if (a[i] == b[j]) {
            //相同的
            return editDistance1(a, b, i - 1, j - 1);
        } else {
            return Collections.min(Arrays.asList(
                    //+1是删除a[i]
                    editDistance1(a, b, i - 1, j) + 1,
                    //+1插入b[j]
                    editDistance1(a, b, i, j - 1) + 1,
                    //+1 是a[i]替换为b[j]
                    editDistance1(a, b, i - 1, j - 1) + 1
            ));
        }
    }

    /**
     * 动态规划来解决
     * 空间复杂度高 O(mn)
     *
     * @param args
     */

    public int editDistance2(char[] a, char[] b) {
        int alength = a.length;
        int blength = b.length;

        if (alength == 0) {
            return blength;
        } else if (blength == 0) {
            return alength;
        }

        int[][] matrix = new int[alength][blength];

        for (int i = 0; i < blength; i++) {
            matrix[0][i] = i;
        }

        for (int j = 0; j < alength; j++) {
            matrix[j][0] = j;
        }

        for (int i = 1; i < alength; i++) {
            for (int j = 1; j < blength; j++)
                if (a[i] == b[j]) {
                    matrix[i][j] = matrix[i - 1][j - 1];
                } else {
                    matrix[i][j] = Collections.min(Arrays.asList(
                            //+1是删除a[i]
                            matrix[i - 1][j] + 1,
                            //+1插入b[j]
                            matrix[i][j - 1] + 1,
                            //+1 是a[i]替换为b[j]
                            matrix[i - 1][j - 1] + 1));
                }
        }

        return matrix[alength - 1][blength - 1];
    }


    /**
     * 动态规划来解决 优化空间复杂度为o(m)
     *
     * @param args
     */

    public int editDistance3(char[] a, char[] b) {
        int alength = a.length;
        int blength = b.length;


        if (alength == 0) {
            return blength;
        } else if (blength == 0) {
            return alength;
        }

        int[][] matrix = new int[2][blength];

        for (int i = 0; i < alength; i++) {
            for (int j = 0; j < blength; j++) {
                if (i == 0) {
                    matrix[0][j] = j;
                }else if(j == 0){
                    matrix[i % 2][j] = i;
                } else {
                    matrix[i % 2][j] = Collections.min(Arrays.asList(
                            //+1是删除a[i]
                            matrix[(i - 1) % 2][j] + 1,
                            //+1插入b[j]
                            j-1 >= 0 ? matrix[i % 2][j - 1] + 1 : 0,
                            //+1 是a[i]替换为b[j]
                            j-1 >= 0 ? matrix[(i - 1) % 2][j - 1] + 1: 0));
                }
            }
        }

        return matrix[alength-1 % 2][blength - 1];
    }


    public static void main(String args[]) {
        EditDistance ed = new EditDistance();
        char[] a = "aa".toCharArray();
        char[] b = "bbaaa".toCharArray();

        int ed1 = ed.editDistance1(a, b, a.length - 1, b.length - 1);
        System.out.println(ed1);

        int ed2 = ed.editDistance2(a, b);
        System.out.println(ed2);

        int ed3 = ed.editDistance3(a, b);
        System.out.println(ed3);
    }
}
