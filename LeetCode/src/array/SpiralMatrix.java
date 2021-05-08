package array;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode.com/problems/spiral-matrix/
 */
public class SpiralMatrix {
    /**
     * 螺旋输出二维数组
     * 输出最外一层，然后递归输出
     * @param matrix 数组
     * @return 输出的值
     */
    public static List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> ans = new ArrayList<>();
        if (matrix.length > 0) {
            printMatrix(matrix, 0, matrix[0].length - 1, 0, matrix.length - 1, ans);
        }
        return ans;
    }

    public static void printMatrix(int[][] matrix, int i1, int i2, int j1, int j2, List<Integer> ans){
        List<Integer> list = new ArrayList<>();
        if (j1 == j2){
            for (int i = i1; i <= i2; i++) {
                list.add(matrix[j1][i]);
            }
        }else if (i1 == i2){
            for (int i = j1; i <= j2; i++) {
                list.add(matrix[i][i1]);
            }
        }else {
            for (int i = i1; i < i2; i++) {
                list.add(matrix[j1][i]);
            }
            for (int i = j1 ; i < j2 ; i++) {
                list.add(matrix[i][i2]);
            }
            for (int i = i2; j2 > j1 && i > i1 ; i--) {
                list.add(matrix[j2][i]);
            }
            for (int i = j2; i > j1 ; i--) {
                list.add(matrix[i][i1]);
            }
        }
        ans.addAll(list);
        if (j1 + 1 > j2 - 1 || i1 + 1 > i2 - 1){
            return;
        }
        printMatrix(matrix,i1 + 1,i2 - 1, j1 + 1, j2 - 1, ans);
    }

    public static void main(String[] args) {
        int[][] nums = new int[][]{
                {1,2,3},
                {4,5,6},
                {7,8,9},
        };
        int[][] nums1 = new int[][]{
                {1,2,3,4},
                {4,5,6,7},
                {7,8,9,10},
        };
        int[][] nums2 = new int[][]{
                {1},
                {2},
                {3},
                {4},
        };
        int[][] nums3 = new int[][]{
                {1,2,3,4}
        };
        System.out.println(spiralOrder(nums3).toString());
    }
}
