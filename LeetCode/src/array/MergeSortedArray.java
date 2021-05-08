package array;

import java.util.Arrays;

public class MergeSortedArray {
    public static void merge(int[] nums1, int m, int[] nums2, int n) {
        if (nums2.length == 0) return;
        int end = nums1.length - 1;
        for (int i = 0; i < nums2.length; i++) {
            nums1[end - i] = nums2[i];
        }
        Arrays.sort(nums1);
    }

    public static void merge2(int[] nums1, int m, int[] nums2, int n) {
        int last=m+n-1;
        while(n>0){
            //如果nums1的最大值小于或等于nums2的最大值，那么将nums2的最大值放到最后一个位置上，
            // 否则将nums1的最大值放到最后一个位置上
            if(m==0||nums1[m-1]<=nums2[n-1]){
                nums1[last--]=nums2[--n];
            }else{
                nums1[last--]=nums1[--m];
            }
        }
    }

    public static void main(String[] args) {
        int[] nums1 = new int[]{1,2,3,0,0,0};
        int[] nums2 = new int[]{2,5,6};
        merge(nums1, 3, nums2, 3);
        System.out.println(Arrays.toString(nums1));
    }
}
