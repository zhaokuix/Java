package array;

import java.util.Arrays;

/**
 * https://leetcode-cn.com/problems/remove-duplicates-from-sorted-array-ii/
 */
public class RemoveDuplicatesFromSortedArrayII {

    public int removeDuplicates(int[] nums) {
        int i = 0;
        for (int n : nums)
            if (i < 2 || n != nums[i-2])
                nums[i++] = n;
        return i;
    }

    public static int removeDuplicates2(int[] nums) {
        int ans = 0;
        for (int n : nums){
            if (ans < 2 || n != nums[ans-2]){
                nums[ans] = n;
                ans++;
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{0,0,1,1,1,1,2,3,3};
        System.out.println(removeDuplicates2(nums) + " " + Arrays.toString(nums));
    }
}
