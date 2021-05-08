package array;

import java.util.*;

/**
 * https://leetcode.com/problems/3sum-closest/
 */
public class Sum3Closest {
    /**
     * 双指针
     * @param nums 传入数组
     * @param target 目标值
     * @return 返回距离target最近的和
     */
    public static int threeSumClosest(int[] nums, int target) {
        int ans = 0;
        int temp = 0;
        Arrays.sort(nums);
        for (int i = 0; i < nums.length - 2; i++) {
            //左指针
            int l = i + 1;
            //右指针
            int r = nums.length - 1;
            while (l < r){
                int sum = nums[i] + nums[l] + nums[r];
                int t = Math.abs(target-sum);
                if (temp == 0 || t < temp){
                    temp = t;
                    ans = sum;
                }
                if (sum < target){
                    l++;
                }else if(sum > target){
                    r--;
                }else {
                    return target;
                }
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int[]nums = new int[]{1, 1, -1, 0};
        int target = 0;
        System.out.println(threeSumClosest(nums, target));
    }
}
