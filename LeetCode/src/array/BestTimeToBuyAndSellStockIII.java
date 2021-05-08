package array;

/**
 * https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-iii/
 */
public class BestTimeToBuyAndSellStockIII {
    /**
     * 以 i 为结尾的dp数组保存起来，以 i 为开始的dp数组也保存起来，然后左右搭配。
     * @param prices 数组
     */
    public static int maxProfit(int[] prices) {
        if (prices.length == 0)return 0;
        int[] max0ToI = new int[prices.length];
        int[] maxItoEnd = new int[prices.length];
        int iMin = prices[0];
        //0到i的最大值
        for (int i = 1; i < prices.length; i++) {
            max0ToI[i] = Math.max(max0ToI[i - 1], prices[i] - iMin);
            iMin = Math.min(iMin, prices[i]);
        }
        //从i开始的最大值,倒着计算
        int iMax = prices[prices.length - 1];
        for (int i = prices.length - 2; i >= 0; i--){
            maxItoEnd[i] = Math.max(maxItoEnd[i + 1], iMax - prices[i]);
            iMax = Math.max(iMax, prices[i]);
        }
        int max = 0;
        for (int i = 0; i + 1 < prices.length; i++) {
            max = Math.max(max,max0ToI[i] + maxItoEnd[i]);
        }
        return max;
    }

    public static void main(String[] args) {
        int[] prices = new int[]{1,2};
        System.out.println(maxProfit(prices));
    }
}
