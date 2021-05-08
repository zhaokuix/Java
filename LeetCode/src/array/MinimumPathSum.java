package array;

/**
 * https://leetcode-cn.com/problems/minimum-path-sum/
 */
public class MinimumPathSum {
    /**
     * 动态规划
     * 令d[i][j]为(i,j)点最小的和
     * d[i][j] = min(d[i][j-1], d[i-1][j]) + grid[i][j];
     * @param grid 输入的数组
     * @return 最小的和
     */
    public static int minPathSum(int[][] grid) {
        int[][] dp = new int[grid.length][grid[0].length];
        dp[0][0] = grid[0][0];
        for (int i = 1; i < dp.length; i++) {
            dp[i][0] = grid[i][0] + dp[i-1][0];
        }
        for (int i = 1; i < dp[0].length; i++) {
            dp[0][i] = grid[0][i] + dp[0][i-1];
        }
        for (int i = 1; i < dp.length; i++) {
            for (int j = 1; j < dp[0].length; j++) {
                dp[i][j] = Math.min(dp[i][j-1], dp[i-1][j]) + grid[i][j];
            }
        }
        return dp[dp.length - 1][dp[0].length - 1];
    }

    /**
     * 动态规划
     * 优化为一维数组
     * dp[j] = min(dp[j-1], dp[j]) + grid[i][j];
     * @param grid 传入的数组
     * @return 最小和
     */
    public static int minPathSum2(int[][] grid) {
        int[] dp = new int[grid[0].length];
        dp[0] = grid[0][0];
        for (int i = 1; i < dp.length; i++) {
            dp[i] = grid[0][i] + dp[i-1];
        }
        for (int i = 1; i < grid.length; i++) {
            //每一行的d[0]需要重新计算
            dp[0] = dp[0] + grid[i][0];
            for (int j = 1; j < grid[0].length; j++) {
                //公式
                dp[j] = Math.min(dp[j-1], dp[j]) + grid[i][j];
            }
        }
        return dp[dp.length - 1];
    }

    public static void main(String[] args) {
        int[][] grid = new int[][]{
                {1,3,1},
                {1,5,1},
                {4,2,1}
        };
        System.out.println(minPathSum2(grid));
    }
}
