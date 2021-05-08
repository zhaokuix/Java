package array;

/**
 * https://leetcode-cn.com/problems/search-in-rotated-sorted-array-ii/
 */
public class SearchInRotatedSortedArrayII {

    /**
     * 如果我们在找mid的过程中，left，mid，right，那么我们要对left和right进行操作，
     * left+1, end-1，去试图skip掉那些重复的数字。
     * @param nums 数组
     * @param target 目标值
     * @return 是否存在
     */
    public static boolean search(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        int mid = 0;
        if (nums.length == 0){
            return false;
        }
        while(left <= right){
            mid = (left + right)/2;
            if (target == nums[mid]){
                return true;
            } else if((nums[mid] == nums[left]) && (nums[mid] == nums[right])){
                left++;
                right--;
            } else if (nums[mid] < nums[right]){//mid小于right说明右半部分有序
                //target > mid 说明在右半部分
                if (nums[mid] < target && nums[right] >= target){
                    left = mid + 1;
                }else{
                    right = mid - 1;
                }
            }else {//mid大于right说明左半部分有序
                if (nums[mid] > target && nums[left] <= target){
                    right = mid - 1;
                }else {
                    left = mid + 1;
                }
            }

        }
        return false;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{1,1,3,1};
        System.out.println(search(nums, 3));
    }
}
