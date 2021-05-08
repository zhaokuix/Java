package array;

import java.util.Arrays;

/**
 * https://leetcode.com/problems/rotate-image/
 * 1 2 3
 * 4 5 6
 * 7 8 9
 *
 * 1 4 7
 * 2 5 8
 * 3 6 9
 */
public class RotateImage {
    /**
     * 转置加翻转
     * @param matrix 数组
     */
    public static void rotate(int[][] matrix) {
        int length = matrix.length;
        //转置
        for (int i = 0; i < length; i++) {
            for (int j = i; j < length; j++) {
                int tmp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = tmp;
            }
        }
        for (int i = 0; i < matrix.length; i++) {
            System.out.println(Arrays.toString(matrix[i]));
        }
        System.out.println("");
        //翻转
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length/2; j++) {
                int tmp = matrix[i][j];
                matrix[i][j] = matrix[i][length - j -1];
                matrix[i][length - j -1] = tmp;
            }
        }
    }

    public static void main(String[] args) {
        int[][] nums = new int[][]{
                {1,2,3,4},
                {5,6,7,8},
                {9,10,11,12},
                {13,14,15,16}
        };
        for (int i = 0; i < nums.length; i++) {
            System.out.println(Arrays.toString(nums[i]));
        }
        System.out.println("先转置再翻转");
        rotate(nums);
        for (int i = 0; i < nums.length; i++) {
            System.out.println(Arrays.toString(nums[i]));
        }
    }
}
