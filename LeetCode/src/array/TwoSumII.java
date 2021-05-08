package array;

import java.util.Arrays;

/**
 * https://leetcode-cn.com/problems/two-sum-ii-input-array-is-sorted/
 */
public class TwoSumII {
    /**
     * 二分查找
     * @param numbers 数组
     * @param target 目标
     * @return 坐标
     */
    public static int[] twoSum(int[] numbers, int target) {
        int index1 = 0,index2 = 0;
        for (int i = 0; i < numbers.length; i++) {
            int t = target - numbers[i];
            index1 = i + 1;
            if (t <= numbers[i]){
                index2 = Arrays.binarySearch(numbers, 0, i, t);
                if (index2 >= 0){
                    index2++;
                    if (index1 < index2) return new int[]{index1, index2};
                    else return new int[]{index2, index1};
                }
            }else {
                index2 = Arrays.binarySearch(numbers, i+1, numbers.length, t);
                if (index2 >= 0){
                    index2++;
                    if (index1 < index2) return new int[]{index1, index2};
                    else return new int[]{index2, index1};
                }
            }
        }
        return new int[]{};
    }

    /**
     * 双指针
     * @param numbers 数组
     * @param target 目标
     * @return 坐标
     */
    public static int[] twoSum2(int[] numbers, int target) {
        int left = 0, right = numbers.length - 1;
        while (left < right){
            if (numbers[left] + numbers[right] == target){
                return new int[]{left + 1, right + 1};
            }else if (numbers[left] + numbers[right] < target){
                left++;
            }else {
                right--;
            }
        }
        return new int[]{};
    }

    public static void main(String[] args) {
        int[] numbers = {2,7,11,15};
        System.out.println(Arrays.toString(twoSum2(numbers, 9)));
    }
}
