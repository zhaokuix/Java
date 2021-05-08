package array;

/**
 * https://leetcode.com/problems/jump-game/
 */
public class JumpGame {
    /**
     * 计算每个点能到达的最远的距离，最后一个点除外，判断是否有能跳到最后一个点的位置
     * @param nums 数组
     * @return 是否能跳到最后一个点
     */
    public static boolean canJump(int[] nums) {
        int maxPosition = 0;
        //最远能跳到的点
        int end = 0;
        boolean ans = false;
        if (nums.length == 1){
            return true;
        }
        for (int i = 0; i < nums.length - 1; i++) {
            //i + nums[i]：当前点能跳到最远的点
            //maxPosition：i到end中所有点中能跳到的最远的点
            maxPosition = Math.max(maxPosition, i + nums[i]);
            if (i == end){
                end = Math.max(maxPosition, end);
            }
            if (end >= nums.length - 1){
                ans = true;
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{1,2};
        System.out.println(canJump(nums));
    }
}
