package array;

import java.util.Arrays;

/**
 * https://leetcode.com/problems/find-first-and-last-position-of-element-in-sorted-array/
 */
public class FindFirstAndLastPositionOfElementInSortedArray {

    /**
     *二分查找Arrays.binarySearch()
     * @param nums 数组
     * @param target 目标值
     * @return target的第一个和最后一个出现的地方
     */
    public static int[] searchRange(int[] nums, int target) {
        int[] ans = new int[]{-1, -1};
        int left = 0, right = nums.length - 1, mid;
        while (left <= right){
            mid = (left + right) / 2;
            if (nums[mid] > target){
                right = mid - 1;
            }else if (nums[mid] < target){
                left = mid + 1;
            }else {
                ans[0] = mid;
                break;
            }
        }
        int t = ans[0];
        while (t+1 <= nums.length - 1 && nums[t+1] == target){
            ans[1] = t+1;
            t++;
        }
        t = ans[0];
        while (t-1 >= 0 && nums[t-1] == target){
            if (ans[1] == -1){
                ans[1] = ans[0];
            }
            ans[0] = t-1;
            t--;
        }
        t = ans[0];
        if (t + 1 > nums.length - 1 || nums[t + 1] != target){
            ans[1] = ans[0];
        }
        return ans;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{5,7,7,8,8,8,10};
        System.out.println(Arrays.toString(searchRange(nums, 8)));
    }
}
