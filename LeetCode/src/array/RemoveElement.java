package array;

import java.util.Arrays;

/**
 * https://leetcode.com/problems/remove-element/
 */
public class RemoveElement {
    /**
     * point1定位，i寻找下一个非val的值
     * @param nums 数组
     * @param val 要删除的目标值
     * @return 删除目标值后的长度
     */
    public static int removeElement(int[] nums, int val) {
        int point1 = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != val){
                nums[point1] = nums[i];
                point1++;
            }
        }
        return point1;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{3, 2, 2, 3};
        System.out.println("length: " + removeElement(nums, 3) + "\nnums:" + Arrays.toString(nums));
    }
}
