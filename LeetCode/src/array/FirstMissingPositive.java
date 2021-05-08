package array;

/**
 * https://leetcode-cn.com/problems/first-missing-positive/
 * 遍历一次数组把大于等于1的和小于数组大小的值放到原数组对应位置，
 * 然后再遍历一次数组查当前下标是否和值对应，如果不对应那这个下标就是答案，
 * 否则遍历完都没出现那么答案就是数组长度加1。
 */
public class FirstMissingPositive {
    /**
     * 空间复杂度不符合要求
     * @param nums 数组
     * @return 目标值
     */
    public static int firstMissingPositive(int[] nums) {
        int ans = 1;
        int [] tmp = new int[nums.length + 1];
        for (int num : nums) {
            if (num <= 0) {
                continue;
            }
            if (num <= nums.length){
                tmp[num - 1] = num;
            }
        }
        for (int i = 0; i < tmp.length; i++) {
            if (tmp[i] != i + 1){
                ans = i + 1;
                break;
            }
        }
        return ans;
    }

    /**
     * 时间复杂度为O(n), 空间复杂度为常数级，满足要求
     * @param nums 数组
     * @return 目标值
     */
    public static int firstMissingPositive1(int[] nums) {
        //6521
        //6251
        //
        int ans = nums.length + 1;
        for (int i = 0; i < nums.length; i++) {
            //一直交换
            while (nums[i] > 0 && nums[i] <= nums.length && nums[i] != nums[nums[i] - 1]){
                int t = nums[nums[i] - 1];
                nums[nums[i] - 1] = nums[i];
                nums[i] = t;
            }
        }
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != i + 1){
                ans = i + 1;
                return ans;
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{1,3,4,5,6};
        System.out.println(firstMissingPositive1(nums));
    }
}
