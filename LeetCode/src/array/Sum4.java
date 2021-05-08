package array;

import java.util.*;

/**
 * https://leetcode.com/problems/4sum/
 */
public class Sum4 {
    /**
     * 双指针
     * @param nums 数组
     * @param target 目标值
     * @return 满足目标的集合
     */
    public static List<List<Integer>> fourSum(int[] nums, int target) {
        Arrays.sort(nums);
        Set<List<Integer>> ans = new HashSet<>();
        for (int i = 0; i < nums.length - 3; i++) {
            for (int j = i + 1 ; j < nums.length - 2; j++) {
                int left = j + 1;
                int right = nums.length -1;
                while (left < right){
                    int sum = nums[i] + nums[j] + nums[left] + nums[right];
                    if (sum > target){
                        right--;
                    }else if(sum < target){
                        left++;
                    }else {
                        ans.add(Arrays.asList(nums[i], nums[j], nums[left], nums[right]));
                        right--;
                        left++;
                    }
                }
            }
        }
        return new ArrayList<>(ans);
    }

    public static void main(String[] args) {
        int[] nums = new int[]{1, 0, -1, 0, -2, 2};
        System.out.println(fourSum(nums, 0));
    }
}
