package array;

import java.math.BigDecimal;
import java.util.Arrays;

/**
 * https://leetcode.com/problems/unique-paths/
 */
public class UniquePaths {
    /**
     * 方法一、排列组合解法
     * 机器人总共要向右走m-1次，要向下走n-1次，总共走m-1+n-1次，
     * 随机取出n-1次剩下的就是m-1的走法，即C(m+n-2,n-1)或者C(m+n-2,m-1)
     * @param a 长度
     * @param b 宽度
     * @return 多少个路径
     */
    public static int uniquePaths(int a, int b) {
        BigDecimal m = BigDecimal.valueOf(a);
        BigDecimal n = BigDecimal.valueOf(b);
        BigDecimal top = BigDecimal.valueOf(1);
        BigDecimal mm = m.add(n).subtract(BigDecimal.valueOf(2));
        BigDecimal nn = n.subtract(BigDecimal.valueOf(1));
        for (int i = 0; i < b-1; i++) {
            top = top.multiply(mm.subtract(BigDecimal.valueOf(i)));
        }
        BigDecimal bottom = BigDecimal.valueOf(1);
        for (int i = 0; i < b-1; i++) {
            bottom = bottom.multiply(nn.subtract(BigDecimal.valueOf(i)));
        }
        return Integer.parseInt(top.divide(bottom).toString());
    }

    /**
     * 动态规划 动态方程：dp[i][j] = dp[i-1][j] + dp[i][j-1]
     * 我们令 dp[i][j] 是到达 i, j 最多路径
     * dp[i][j] = dp[i-1][j] + dp[i][j-1]
     * 注意，对于第一行 dp[0][j]，或者第一列 dp[i][0]，由于都是在边界，所以只能为 1
     * 时间复杂度：O(m*n)O(m∗n)
     * 空间复杂度：O(m * n)O(m∗n)
     * 优化：因为我们每次只需要 dp[i-1][j],dp[i][j-1]
     * 所以我们只要记录这两个数，直接看代码吧！
     * @param m 行
     * @param n 列
     * @return int
     */
    public static int uniquePaths2(int m, int n) {
        int[][] dp = new int[m][n];
        for (int i = 0; i < dp.length; i++) {
            dp[i][0] = 1;
        }
        Arrays.fill(dp[0], 1);
        for (int i = 1; i < dp.length; i++) {
            for (int j = 1; j < dp[0].length; j++) {
                dp[i][j] = dp[i-1][j] + dp[i][j-1];
            }
        }
        return dp[m-1][n-1];
    }

    /**
     * 动态规划，滚动数组将二维数组降为一维数组
     * 一维数组动态规划公式：dp[j] = dp[j] + dp[j-1];
     * @param m 行
     * @param n 列
     * @return int
     */
    public static int uniquePaths3(int m, int n) {
        int[] dp = new int[n];
        Arrays.fill(dp, 1);
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[j] = dp[j] + dp[j - 1];
            }
        }
        return dp[n-1];
    }

    public static void main(String[] args) {
        System.out.println(uniquePaths3(7,3));
    }
}
