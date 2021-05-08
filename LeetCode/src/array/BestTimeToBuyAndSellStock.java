package array;

/**
 * https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock/
 */
public class BestTimeToBuyAndSellStock {
    /**
     * 暴力循环
     * @param prices 数组
     * @return 最大差值
     */
    public static int maxProfit(int[] prices) {
        int max = 0;
        for (int i = 0; i < prices.length; i++) {
            for (int j = i + 1; j < prices.length; j++) {
                if (prices[j] - prices[i] > 0) {
                    max = Math.max(max, prices[j] - prices[i]);
                }
            }
        }
        return max;
    }

    /**
     * 动态规划
     * 前i天的最大收益 = max{前i-1天的最大收益，第i天的价格-前i-1天中的最小价格}
     * @param prices 数组
     * @return 最大差值
     */
    public static int maxProfit2(int[] prices) {
        if (prices.length == 0)return 0;
        int[] max = new int[prices.length];
        int iMin = prices[0];
        for (int i = 1; i < prices.length; i++) {
            max[i] = Math.max(max[i - 1], prices[i] - iMin);
            iMin = Math.min(iMin, prices[i]);
        }
        return max[max.length - 1];
    }

    /**
     * 动态规划 优化空间
     * 前i天的最大收益 = max{前i-1天的最大收益，第i天的价格-前i-1天中的最小价格}
     * @param prices 数组
     * @return 最大差值
     */
    public static int maxProfit3(int[] prices) {
        if (prices.length == 0)return 0;
        int max = 0;
        int iMin = prices[0];
        for (int i = 1; i < prices.length; i++) {
            max = Math.max(max, prices[i] - iMin);
            iMin = Math.min(iMin, prices[i]);
        }
        return max;
    }

    public static void main(String[] args) {
        int[] prices = new int[]{7,1,5,3,6,4};
        System.out.println(maxProfit3(prices));
    }
}
