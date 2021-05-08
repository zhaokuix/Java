package array;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * https://leetcode-cn.com/problems/longest-consecutive-sequence/
 */
public class LongestConsecutiveSequence {
    /**
     * 哈希表的使用,key表示第i个数，value表示第i个数的最大连续长度
     * @param nums 数组
     * @return 最大连续长度
     */
    public static int longestConsecutive(int[] nums) {
        Map<Integer, Integer> map = new ConcurrentHashMap<>();
        for (int num : nums) {
            map.put(num, 1);
        }
        map.forEach((key, value) -> {
            int keyI = key;
            while(!Objects.isNull(map.get(++keyI))){
                map.put(key, ++value);
            }
        });
        final int[] longest = {0};
        map.values().forEach(value -> longest[0] = Math.max(longest[0], value));
        return longest[0];
    }

    /**
     * 排序
     * @param nums 数组
     * @return 最大连续长度
     */
    public static int longestConsecutive2(int[] nums) {
        if (nums.length == 0)return 0;
        Arrays.sort(nums);
        int[] longest = new int[nums.length];
        Arrays.fill(longest, 1);
        int t = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == t + 1){
                longest[i] = longest[i - 1] + 1;
                t = nums[i];
            }else if (nums[i] == t){
                longest[i] = longest[i - 1];
            }else {
                t = nums[i];
            }
        }
        int max = 1;
        for (int value : longest) {
            max = Math.max(value, max);
        }
        return max;
    }

    public static void main(String[] args) {
        int[] nums = {100, 4, 200, 1, 3, 2};
        System.out.println(longestConsecutive2(nums));
    }
}