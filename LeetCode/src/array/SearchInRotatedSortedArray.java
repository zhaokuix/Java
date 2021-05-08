package array;

/**
 * https://leetcode-cn.com/problems/search-in-rotated-sorted-array/
 */
public class SearchInRotatedSortedArray {
    /**
     * 要求查找时间复杂度为O(log n)----二分查找
     * @param nums 数组
     * @param target 目标值
     * @return 目标值位置
     */
    public static int search(int[] nums, int target) {
        //0,1,2,4,5,6,7
        //5,6,7,0,1,2,4
        int left = 0;
        int right = nums.length - 1;
        int mid = 0;
        if (nums.length == 0){
            return -1;
        }
        while(left <= right){
            mid = (left + right)/2;
            if (target == nums[mid]){
                return mid;
            }else if (nums[mid] < nums[right]){//mid小于right说明右半部分有序
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
        return -1;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{4,5,6,7,0,1,2};
        System.out.println(search(nums, 0));
    }
}
