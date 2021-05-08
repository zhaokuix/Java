package array;

import java.util.Arrays;

/**
 * 快速排序算法
 */
public class QuickSort {
    public static void quickSort(int[] array, int low, int high){
        if (low < high){
            int middle = getMiddleIndex(array, low, high);
            quickSort(array, low, middle - 1);
            quickSort(array, middle + 1, high);
        }
    }
    public static int getMiddleIndex(int[] array, int low, int high){
            int middle = array[low];
            while (low < high){
                //从右向左，找到小于middle的数据，放到左边
                while (low < high && array[high] >= middle){
                    high--;
                }
                array[low] = array[high];
                //从左向右找到大于middle的值放到右边
                while (low < high && array[low] <= middle){
                    low++;
                }
                array[high] = array[low];
            }
            array[low] = middle;
            return low;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{5,8,2,6,4,7,9,2,3};
        quickSort(nums, 0, nums.length - 1);
        System.out.println(Arrays.toString(nums));
    }
}
