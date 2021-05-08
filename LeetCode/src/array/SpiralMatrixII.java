package array;

import java.util.Arrays;

/**
 * https://leetcode.com/problems/spiral-matrix-ii/
 */
public class SpiralMatrixII {
    /**
     * Given a positive integer n, generate a square matrix filled with elements from 1 to n2 in spiral order.
     *
     * @param n 矩阵大小
     * @return 螺旋矩阵
     */
    public static int[][] generateMatrix(int n) {
        if (n < 1) {
            return new int[][]{};
        }
        //生成矩阵
        int[][] matrix = new int[n][n];
        int t = 1;
        generateMatrix(matrix, 0, matrix.length - 1,0,matrix.length - 1, t);
        return matrix;
    }

    /**
     * 螺旋生成矩阵
     * @param matrix 要生成的矩阵
     * @param i1 0
     * @param i2 矩阵宽度
     * @param j1 0
     * @param j2 矩阵高度
     * @param num 从num开始螺旋
     */
    public static void generateMatrix(int[][] matrix, int i1, int i2, int j1, int j2, int num){
        if (j1 == j2){
            for (int i = i1; i <= i2; i++) {
                matrix[j1][i] = num++;
            }
        }else if (i1 == i2){
            for (int i = j1; i <= j2; i++) {
                matrix[i][i1] = num++;
            }
        }else {
            for (int i = i1; i < i2; i++) {
                matrix[j1][i] = num++;
            }
            for (int i = j1 ; i < j2 ; i++) {
                matrix[i][i2] = num++;
            }
            for (int i = i2; j2 > j1 && i > i1 ; i--) {
                matrix[j2][i] = num++;
            }
            for (int i = j2; i > j1 ; i--) {
                matrix[i][i1] = num++;
            }
        }
        if (j1 + 1 > j2 - 1 || i1 + 1 > i2 - 1){
            return;
        }
        generateMatrix(matrix,i1 + 1,i2 - 1, j1 + 1, j2 - 1, num);
    }

    public static void main(String[] args) {
        for (int[] an : generateMatrix(3)) {
            System.out.println(Arrays.toString(an));
        }
    }

}
