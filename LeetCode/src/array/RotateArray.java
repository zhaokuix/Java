package array;

import java.util.Arrays;

/**
 * https://leetcode-cn.com/problems/rotate-array/
 */
public class RotateArray {

    /**
     * 暴力循环
     * @param nums 数组
     * @param k 移动次数
     */
    public static void rotate(int[] nums, int k) {
        while (k-- > 0){
            int t = nums[nums.length - 1];
            System.arraycopy(nums, 0, nums, 1, nums.length - 1);
            nums[0] = t;
        }
    }

    /**
     * 空间换时间(不符合要求)
     * @param nums 数组
     * @param k 移动次数
     */
    public static void rotate2(int[] nums, int k) {
        int[] tmp = new int[k];
        if (nums.length > k){
            System.arraycopy(nums, nums.length -k, tmp, 0, k);
            System.arraycopy(nums, 0, nums, k, nums.length -k);
            System.arraycopy(tmp, 0, nums, 0, k);
        }else {
            while (k-- > 0){
                int t = nums[nums.length - 1];
                System.arraycopy(nums, 0, nums, 1, nums.length - 1);
                nums[0] = t;
            }
        }
    }

    public static void main(String[] args) {
        int[] nums = new int[]{1,2};
        rotate(nums, 3);
        System.out.println(Arrays.toString(nums));
    }
}
