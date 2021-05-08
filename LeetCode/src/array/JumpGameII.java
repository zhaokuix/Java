package array;

/**
 * https://leetcode.com/problems/jump-game-ii/
 */
public class JumpGameII {

    /**
     * 贪心算法，遍历一遍数组i到end中找到能到达的最远的距离maxPosition，
     * 然后将end变成maxPosition，此时如果end>length - 1,说明可以到达终点了
     * 如果此时end<length - 1,再从上次的endOld处开始遍历，遍历到此时的end，找到能到达的最远的距离maxPosition
     * 然后再把end变成maxPosition，直到end>length - 1
     * @param nums 数组
     * @return 步数
     */
    public static int jump(int[] nums) {
        int length = nums.length;
        int end = 0;
        int maxPosition = 0;
        int steps = 0;
        for (int i = 0; i < length - 1; i++) {
            if (end > length - 1){
                break;
            }
            maxPosition = Math.max(maxPosition, i + nums[i]);
            if (i == end) {
                end = maxPosition;
                steps++;
            }
        }
        return steps;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{1,2,3,4,5,1,1,2};
        System.out.println(jump(nums));
    }
}
