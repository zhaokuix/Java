package greedy;

/**
 * https://leetcode-cn.com/problems/gas-station/
 */
public class GasStation {
    /**
     * 初始化 total_tank 和 curr_tank 为 0 ，并且选择 0 号加油站为起点。
     * 遍历所有的加油站：
     * 每一步中，都通过加上 gas[i] 和减去 cost[i] 来更新 total_tank 和 curr_tank 。
     * 如果在 i 号加油站， curr_tank < 0 ，将 i + 1 号加油站作为新的起点，同时重置 curr_tank = 0 ，让油箱也清空。
     * 如果 total_tank < 0 ，返回 -1 ，否则返回 starting station。
     * 一个站的收益如果小于0，肯定不能作为起点；而连续的多个站也可以等效地看做一个站，如果其累积收益小于0，就跳过，寻找下一个。
     * @param gas
     * @param cost
     * @return
     */
    public static int canCompleteCircuit(int[] gas, int[] cost) {
        int n = gas.length;

        int total_tank = 0;
        int curr_tank = 0;
        int starting_station = 0;
        for (int i = 0; i < n; ++i) {
            //总和必须大于等于0，否则不能完成绕行
            total_tank += gas[i] - cost[i];
            curr_tank += gas[i] - cost[i];
            if (curr_tank < 0) {
                // 一个站的收益如果小于0，肯定不能作为起点；而连续的多个站也可以等效地看做一个站，如果其累积收益小于0，就跳过，寻找下一个。
                starting_station = i + 1;
                // 还原到初始状态
                curr_tank = 0;
            }
        }
        return total_tank >= 0 ? starting_station : -1;
    }

    /**
     * 暴力循环
     * @param gas
     * @param cost
     * @return
     */
    public static int canCompleteCircuit2(int[] gas, int[] cost) {
        int N = cost.length;
        for (int i = 0; i < gas.length; ++i)
        {
            int k = 0;
            int oil = gas[i];
            while (true)
            {
                oil -= cost[(i + k) % N];
                if (oil < 0)
                    break;
                if (k == N)
                    return i;
                ++k;
                oil += gas[(i + k) % N];
            }
        }
        return -1;
    }
    public static void main(String[] args) {
        int[] gas = {1,2};
        int[] cost = {2,1};
        System.out.println(canCompleteCircuit(gas, cost));
    }
}
