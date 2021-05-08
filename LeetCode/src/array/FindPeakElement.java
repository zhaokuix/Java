package array;

/**
 * https://leetcode-cn.com/problems/find-peak-element/
 */
public class FindPeakElement {

    /**
     * 遍历一遍找到大于左右相邻元素的值(时间复杂度不是 O(logN) 不满足要求)
     * @param nums 数组
     * @return 峰值
     */
    public int findPeakElement(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            if (nums.length == 1) return i;
            if (i == nums.length - 1 && nums[i] > nums[i - 1])return i;
            if (i > 0 && nums[i] > nums[i-1] && nums[i] > nums[i + 1])return i;
        }
        return 0;
    }

    /**
     * 为什么二分查找大的那一半一定会有峰值呢？
     * （即nums[mid]<nums[mid+1]时，mid+1~N一定存在峰值）
     * 我的理解是，首先已知 nums[mid+1]>nums[mid]，那么mid+2只有两种可能，
     * 一个是大于mid+1，一个是小于mid+1，小于mid+1的情况，那么mid+1就是峰值，
     * 大于mid+1的情况，继续向右推，如果一直到数组的末尾都是大于的，那么可以肯定最后一个元素是峰值，
     * 因为nums[nums.length]=负无穷
     * @param nums 数组
     * @return 峰值
     */
    public static int findPeakElement2(int[] nums) {
        int left = 0;
        int right = nums.length - 1;
        int mid;
        while (left <= right){
            mid = (left + right) / 2;
            if (right == 0) return 0;
            if (mid > 0 && mid == right) return mid;
            if (mid > 0 && mid < right && nums[mid] > nums[mid+1] && nums[mid] > nums[mid-1]) return mid;
            if (mid < right && nums[mid]<nums[mid+1]){
                left = mid + 1;
            }else {
                right = mid - 1;
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        int[] nums = {1,2};
        System.out.println(findPeakElement2(nums));
    }
}
