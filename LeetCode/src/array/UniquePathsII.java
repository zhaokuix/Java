package array;

import java.util.Arrays;

/**
 * https://leetcode-cn.com/problems/unique-paths-ii/
 */
public class UniquePathsII {
    /**
     * 动态规划，（i,j）位置有障碍时，令dp[i][j]=0
     * @param obstacleGrid 二维数组
     * @return int
     */
    public static int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int[][] dp = new int[obstacleGrid.length][obstacleGrid[0].length];
        int t = 0;
        for (int i = 0; i < dp.length; i++) {
            t = obstacleGrid[i][0];
            if (t == 0){
                dp[i][0] = 1;
            }else {//t=1时说明遇到了障碍，机器人无法到达更下一个位置了，直接跳出就好，java默认数组初始化为0
                break;
            }
        }
        t = 0;
        for (int i = 0; i < dp[0].length; i++) {
            t = obstacleGrid[0][i];
            if (t == 0){
                dp[0][i] = 1;
            }else {//t=1时说明遇到了障碍，机器人无法到达更下一个位置了，直接跳出就好，java默认数组初始化为0
                break;
            }
        }
        for (int i = 1; i < dp.length; i++) {
            for (int j = 1; j < dp[0].length; j++) {
                if (obstacleGrid[i][j] == 1){
                    dp[i][j] = 0;
                }else {
                    dp[i][j] = dp[i-1][j] + dp[i][j-1];
                }
            }
        }
        return dp[dp.length - 1][dp[0].length -1];
    }

    public static void main(String[] args) {
        int[][] obstacleGrid = new int[][]{
                {1},
        };
        System.out.println(uniquePathsWithObstacles(obstacleGrid));
    }
}
