package array;

/**
 * https://leetcode.com/problems/search-insert-position/
 */
public class SearchInsertPosition {
    public static int searchInsert(int[] nums, int target) {
        return binarySearchForThisQuestion(nums, target);
    }
    public static int binarySearchForThisQuestion(int[] array, int target){
        int left = 0;
        int right = array.length - 1;
        int mid = (left + right)/2;;
        if (array.length == 0){
            return -1;
        }
        while(left <= right){
            mid = (left + right)/2;
            if (array[mid] == target){
                return mid;
            }else if (array[mid] < target){
                left = mid + 1;
            }else if (array[mid] > target){
                right = mid - 1;
            }
        }
        if (array[mid] > target){
            return mid;
        }else {
            return mid + 1;
        }
    }

    public static void main(String[] args) {
        int[] nums = new int[]{1,3,5};
        System.out.println(searchInsert(nums, 0));
    }
}
