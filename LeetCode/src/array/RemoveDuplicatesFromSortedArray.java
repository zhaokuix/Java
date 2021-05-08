package array;

import java.util.Arrays;

/**
 * https://leetcode.com/problems/remove-duplicates-from-sorted-array/
 */
public class RemoveDuplicatesFromSortedArray {
    /**
     * 单指针遍历
     * @param nums 数组
     * @return 个数
     */
    public static int removeDuplicates(int[] nums) {
        int t;
        int moveCount = 0;//移动了多少个元素
        for (int i = 0; i < nums.length - moveCount; i++) {
            for (int j = i + 1; j < nums.length - moveCount; j++) {
                if (nums[i] == nums[j]){
                    t = nums[j];
                    for (int k = j + 1; k < nums.length; k++) {
                        nums[k-1] = nums[k];
                    }
                    nums[nums.length - 1] = t;
                    moveCount++;
                    j--;
                }
            }
        }
        return nums.length - moveCount;
    }

    /**
     * 优化速度，数组排好序的，可以加快时间
     * @param nums 数组
     * @return 个数
     */
    public static int removeDuplicates2(int[] nums) {
        int moveCount = 0;//移动了多少个元素
        for (int i = 0; i < nums.length - moveCount; i++) {
            for (int j = i + 1; j < nums.length - moveCount && nums[j] == nums[i]; j++) {
                for (int k = j + 1; k < nums.length - moveCount; k++) {
                    nums[k-1] = nums[k];
                }
                moveCount++;
                j--;
            }
        }
        return nums.length - moveCount;
    }

    /**
     * 双指针O(n)优化
     * point1指向不重复的最后一个值，point2向下寻找不重复的值，找到了以后就把该值放到point1+1的位置
     * @param nums 数组
     * @return 个数
     */
    public static int removeDuplicates3(int[] nums) {
        int point1 = 0;
        for (int point2 = 1; point2 < nums.length; point2++) {
            if (nums[point1] != nums[point2]){
                point1++;
                nums[point1] = nums[point2];
            }
        }
        return point1 + 1;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{0,0,1,1,1,2,2,3,3,4};
        System.out.println("count:" + removeDuplicates3(nums) + "\nnums" + Arrays.toString(nums));
    }
}
