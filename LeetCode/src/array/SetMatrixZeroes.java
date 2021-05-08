package array;

import java.util.Arrays;

public class SetMatrixZeroes {
    /**
     * 矩阵中某个数为零，则将该数所在行的第一个数置零，所在列的第一个数置零，
     * 即matrix[0][j] = matrix[i][0] = 0;，
     * 这样并不会影响该列该行首个数的取值，因为他们最后都会被置零。即让首行首列记录哪一列有零，哪一行有零
     * 遍历矩阵中非首行首列的每个元素，如果所在行首或者列首元素为零，则说明该行该列应该都为零，将该元素置零，最后达到目的
     * 第一步操作可能会让首行首列是否有零这个信息损失掉，因为首行首列被用来存储其他信息了，会改变他们的取值，
     * 所以再定义两个变量row0,col0记录首行首列是否有零，并且这一步需要放在前面
     *
     * 思路来源：https://leetcode-cn.com/u/bao-bao-ke-guai-liao/
     */
    public static void setZeroes(int[][] matrix) {
        boolean row0 = false, col0 = false;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == 0){
                    if (i==0)row0 = true;
                    if (j==0)col0 = true;
                    matrix[0][j] = 0;
                    matrix[i][0] = 0;
                }
            }
        }

        for (int i = 1; i < matrix.length; i++) {
            for (int j = 1; j < matrix[0].length; j++) {
                if (matrix[i][0] == 0 || matrix[0][j] == 0){
                    matrix[i][j] = 0;
                }
            }
        }

        if (row0) Arrays.fill(matrix[0], 0);
        if (col0) for (int i = 0; i < matrix.length; i++) {
            matrix[i][0] = 0;
        }

    }

    public static void main(String[] args) {
        int[][] matrix = new int[][]{
                {0,1,1},
                {1,0,1},
                {1,1,1},
        };
        setZeroes(matrix);
        for (int[] ints : matrix) {
            System.out.println(Arrays.toString(ints));
        }
    }
}
