package array;

import java.util.Arrays;

/**
 * https://leetcode-cn.com/problems/sort-colors/
 */
public class SortColors {

    /**
     * 两趟扫描
     * @param nums 数组
     */
    public static void sortColors(int[] nums) {
        int red =0, white = 0, blue = 0;
        for (int num : nums) {
            if (num == 0) red++;
            if (num == 1) white++;
            if (num == 2) blue++;
        }
        for (int i = 0; i < nums.length; i++) {
            if (red > 0){
                nums[i] = 0;
                red--;
            }
            else if (white > 0){
                nums[i] = 1;
                white--;
            }else if (blue > 0){
                nums[i] = 2;
                blue--;
            }
        }
    }

    /**
     * 快速排序
     * @param nums 数组
     */
    public static void sortColors2(int[] nums) {
        QuickSort.quickSort(nums, 0, nums.length - 1);
    }

    /**
     * 快速排序针对该题进行操作，三指针
     * @param nums 数组
     */
    public static void sortColors3(int[] nums) {
       int left = 0, right = nums.length - 1;
        for (int i = 0; i<=right; i++) {
            if (nums[i] == 0){
                int temp =nums[left];
                nums[left] = nums[i];
                nums[i] = temp;
                left++;
            };
            if (nums[i] == 2){
                int temp =nums[right];
                nums[right] = nums[i];
                nums[i] = temp;
                right--;
                i--;
            }
        }
    }

    public static void main(String[] args) {
        int[] nums = new int[]{2,0,2,1,1,0};
        sortColors3(nums);
        System.out.println(Arrays.toString(nums));
    }
}
