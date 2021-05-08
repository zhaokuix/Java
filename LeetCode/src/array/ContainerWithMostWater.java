package array;

/**
 * https://leetcode.com/problems/container-with-most-water/
 */
public class ContainerWithMostWater {
    /**
     * 方法一、暴力循环
     * @param height 输入数组
     * @return 返回最大面积
     */
    public static int maxArea(int[] height) {
        int area = 0;
        for (int i = 0; i < height.length; i++){
            for (int j = i + 1; j < height.length; j++){
                int s = Math.min(height[i], height[j]) * (j - i);
                if (area < s){
                    area = s;
                }
            }
        }
        return area;
    }

    /**
     * 方法二、双指针加快速度
     * @param height 输入数组
     * @return 返回最大面积
     */
    public static int maxArea1(int[] height) {
        int area = 0;
        for (int i = 0, j = height.length - 1; i < j; ){
            int s = Math.min(height[i], height[j]) * (j - i);
            if (area < s){
                area = s;
            }
            if (height[i] < height[j]) {
                i++;
            } else {
                j--;
            }
        }
        return area;
    }

    public static void main(String[] args) {
        int[] input = new int[]{1,8,6,2,5,4,8,3,7};
        System.out.println(maxArea1(input));
    }
}
