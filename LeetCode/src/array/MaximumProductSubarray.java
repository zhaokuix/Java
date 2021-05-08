package array;

/**
 * https://leetcode-cn.com/problems/maximum-product-subarray/
 */
public class MaximumProductSubarray {

    /**
     *考虑当前位置如果是一个负数的话，那么我们希望以它前一个位置结尾的某个段的积也是个负数，这样就可以负负得正，
     *并且我们希望这个积尽可能「负得更多」，即尽可能小。如果当前位置是一个正数的话，
     *我们更希望以它前一个位置结尾的某个段的积也是个正数，并且希望它尽可能地大。
     * 公式：
     * dpMax[i] = Math.max(dpMax[i - 1] * nums[i], Math.max(nums[i], dpMin[i - 1] * nums[i]))
     * dpMin[i] = Math.min(dpMin[i-1] * nums[i], Math.min(nums[i], dpMax[i - 1] * nums[i]))
     * @param nums
     * @return
     */
    public static int maxProduct(int[] nums) {
        if (nums.length == 0) return 0;
        int[] dpMax = new int[nums.length];
        int[] dpMin = new int[nums.length];
        int ans = nums[0];
        dpMax[0] = nums[0];
        dpMin[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            dpMax[i] = Math.max(dpMax[i - 1] * nums[i], Math.max(nums[i], dpMin[i - 1] * nums[i]));
            dpMin[i] = Math.min(dpMin[i-1] * nums[i], Math.min(nums[i], dpMax[i - 1] * nums[i]));
        }
        for (int max : dpMax) {
            ans = Math.max(ans, max);
        }
        return  ans;
    }

    public static void main(String[] args) {
        int[] nums = {-2,3,-2,4};
        System.out.println(maxProduct(nums));
    }
}
