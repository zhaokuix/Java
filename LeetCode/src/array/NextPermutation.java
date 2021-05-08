package array;

import java.util.Arrays;

/**
 * 全排列,按字典顺序找到比现有字典顺序大的，下一个字典顺序
 * 比如1234，下一个全排列是1243
 * 此方法在C++ STL中有相应的实现
 * 现在实现Java版
 * https://leetcode.com/problems/next-permutation/
 */
public class NextPermutation {
    /**
     * 全排列（字典顺序）
     * @param nums 数组
     * @return 是否有下一个全排列
     */
    public static boolean nextPermutation(int[] nums) {
        //123654
        //124653
        //没有下一个全排列
        if (nums.length <= 1){
            return false;
        }
        //从后往前找找到第一个不满足降序的数(要考虑到重复的数字)
        int noDescending = nums.length - 2;
        while (noDescending >=0 && nums[noDescending] >= nums[noDescending + 1]){
            noDescending --;
        }
        //全是降序，没有全排列
        if (noDescending == -1){
            //按题目要求，没有更大的字典序时，返回最小的字典序
            Arrays.sort(nums);
            return false;
        }
        //从noDescending往后找，找到大于nums[noDescending]的最小的数
        int k = noDescending + 1;
        while (k < nums.length && nums[k] > nums[noDescending]){
            k++;
        }
        //交换nums[noDescending]和nums[k]
        swap(nums, noDescending, k - 1);
        //将noDescending后面的数据进行升序排列（变成最小字典序）
        Arrays.sort(nums,noDescending + 1, nums.length);
        return true;
    }
    public static void swap(int[] array, int i, int j) {
        int t =array[i];
        array[i] = array[j];
        array[j] =t;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{5,1,1};
        if (nextPermutation(nums)) nextPermutation(nums);
        System.out.println(Arrays.toString(nums));
    }
}
