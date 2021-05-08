package array;

import java.util.ArrayDeque;
import java.util.Deque;

public class MaximalRectangle {
    /**
     * 每一层看作是柱状图，可以套用84题柱状图的最大面积。
     *
     * 第一层柱状图的高度["1","0","1","0","0"]，最大面积为1；
     *
     * 第二层柱状图的高度["2","0","2","1","1"]，最大面积为3；
     *
     * 第三层柱状图的高度["3","1","3","2","2"]，最大面积为6；
     *
     * 第四层柱状图的高度["4","0","0","3","0"]，最大面积为4；
     * @param matrix 二维数组
     * @return 最大值
     */
    public static int maximalRectangle(char[][] matrix) {
        if (matrix.length == 0) return 0;
        int maxArea = 0;
        int[] heights = new int[matrix[0].length];
        for (char[] chars : matrix) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (chars[j] == '1') {
                    heights[j] = heights[j] + 1;
                }else {
                    heights[j] = 0;
                }
            }
            maxArea = Math.max(maxArea, largestRectangleArea(heights));//算出每行的最大面积
        }
        return maxArea;
    }

    /**
     * 单调栈计算最大面积
     * @param heights 高度集合
     * @return 最大矩形面积
     */
    public static int largestRectangleArea(int[] heights) {
        if (heights.length == 0) return 0;
        //将数组前后两端增加一个0，方便计算
        int[] tmp = new int[heights.length + 2];
        System.arraycopy(heights,0, tmp, 1, heights.length);
        int maxArea = 0;
        Deque<Integer> deque = new ArrayDeque<>();
        for (int i = 0; i < tmp.length; i++) {
            while (!deque.isEmpty() && tmp[i] < tmp[deque.peek()]){
                int h = tmp[deque.pop()];//当前高度，当前高度右边界为i,左边界为当前高度下面的那个坐标
                maxArea = Math.max(maxArea,  (i - deque.peek() - 1) * h);
            }
            deque.push(i);
        }
        return maxArea;
    }

    public static void main(String[] args) {
        char[][] matrix = new char[][]{
                {'0', '1'},
                {'1', '0'}
        };
        System.out.println(maximalRectangle(matrix));
    }

}
