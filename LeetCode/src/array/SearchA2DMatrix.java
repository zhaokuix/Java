package array;

import java.util.Arrays;

/**
 * https://leetcode-cn.com/problems/search-a-2d-matrix/
 */
public class SearchA2DMatrix {
    /**
     * 二维数组二分查找
     * @param matrix 数组
     * @param target 目标值
     * @return 是否有该值
     */
    public static boolean searchMatrix(int[][] matrix, int target) {
        if (matrix.length == 0 || matrix[0].length == 0) return false;
        boolean ans = false;
        for (int i = 0; i < matrix.length; i++) {
            if (target <= matrix[i][matrix[0].length - 1]){
                if (Arrays.binarySearch(matrix[i], target) > -1) ans =true;
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int[][] matrix = new int[][]{{}};
        System.out.println(searchMatrix(matrix, 13));
    }
}
