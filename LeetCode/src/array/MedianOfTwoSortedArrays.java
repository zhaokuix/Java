package array;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *https://leetcode.com/problems/median-of-two-sorted-arrays/
 */
public class MedianOfTwoSortedArrays {
    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        List<Integer> list = new ArrayList<>();
        for (int value : nums1) list.add(value);
        for (int value : nums2) list.add(value);
        Collections.sort(list);
        if (list.size() % 2 ==1){
            return list.get(list.size()/2);
        }else {
            return ((long)list.get(list.size()/2) + (long)list.get(list.size()/2-1))/2.0;
        }
    }

    public static void main(String[] args) {
        int[] nums1 = new int[]{1, 3};
        int[] nums2 = new int[]{2};
        System.out.println(findMedianSortedArrays(nums1, nums2));
    }
}
