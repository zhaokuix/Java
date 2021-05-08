package array;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 *https://leetcode.com/problems/two-sum/
 */
public class TwoSum {
    /**
     * 解法一，暴力遍历
     * @param nums 数组
     * @param target 目标
     * @return 数字下标
     */
    public static int[] twoSum(int[] nums, int target) {
        for(int i = 0; i < nums.length; i++){
            for (int j = i + 1; j < nums.length; j++){
                if ( nums[i] + nums[j] == target){
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    /**
     * 解法二、使用hashmap
     * @param nums 数组
     * @param target 目标
     * @return 数字下标
     */
    public static int[] twoSum2(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0 ; i < nums.length; i++){
            map.put(nums[i], i);
        }
        for (int i = 0; i < nums.length; i++){
            int s = target - nums[i];
            if (map.containsKey(s) && map.get(s) != i){
                return new int[]{i, map.get(s)};
            }
        }
        return null;
    }

    /**
     * 解法三、一次使用hashmap
     * @param nums 数组
     * @param target 目标
     * @return 数字下标
     */
    public static int[] twoSum3(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0 ; i < nums.length; i++){
            map.put(nums[i], i);
        }
        for (int i = 0; i < nums.length; i++){
            int s = target - nums[i];
            if (map.containsKey(s) && map.get(s) != i){
                return new int[]{i, map.get(s)};
            }
        }
        return null;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{3,2,4};
        int target = 6;
        System.out.println(Arrays.toString(twoSum(nums, target)));
    }
}
