package array;

import java.util.Arrays;

/**
 * https://leetcode-cn.com/problems/find-minimum-in-rotated-sorted-array/
 */
public class FindMinimumInRotatedSortedArray {

    /**
     * 最简单的办法排序
     * @param nums 旋转后的数组
     * @return 最小值
     */
    public int findMin(int[] nums) {
        Arrays.sort(nums);
        return nums[0];
    }

    /**
     * 利用数组性质，分情况讨论
     * 找到mid，如果最右边的数比mid大，说明右侧有序,mid为右侧最小值
     * 如果mid比最左侧数的大说明左侧有序，左侧最小值为最左侧的数
     * 递归
     * @param nums 旋转后的数组
     * @return 最小值
     */
    public static int findMin1(int[] nums) {
        if (nums.length == 0) return 0;
        int left = 0;
        int right = nums.length - 1;
        int minAns = nums[0];
        while (left <= right){
            int mid = (left + right) / 2;
            if (nums[mid] <= nums[right]){//说明右侧有序
                minAns = Math.min(minAns, nums[mid]);
                right = mid - 1;
            }else if (nums[mid] >= nums[left]){//说明左侧有序
                minAns = Math.min(minAns, nums[left]);
                left = mid + 1;
            }
        }
        return minAns;
    }

    public static void main(String[] args) {
        int[] nums = {4,5,6,7,0,1,2};
        System.out.println(findMin1(nums));
    }
}
