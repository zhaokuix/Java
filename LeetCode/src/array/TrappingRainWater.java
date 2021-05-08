package array;

import java.util.Stack;

/**
 * https://leetcode.com/problems/trapping-rain-water/
 */
public class TrappingRainWater {
    /**
     * 思路，找到当前位置的左边的最大值，和右边的最大值
     * 用左右边最大值较小的一个减去当前位置的高度，即为当前位置存的雨水量
     * @param height 高度数组
     * @return 所装的雨水量
     */
    public static int trap(int[] height) {
        int ans = 0;
        int leftMax, rightMax;
        for (int i = 0; i < height.length; i++) {
            leftMax = 0; rightMax = 0;
            for (int j = 0; j < i; j++) {
                leftMax = Math.max(height[j], leftMax);
            }
            for (int k = height.length - 1; k > i; k--) {
                rightMax = Math.max(height[k], rightMax);
            }
            if (Math.min(leftMax, rightMax) > height[i]){
                ans += Math.min(leftMax, rightMax) - height[i];
            }
        }
        return ans;
    }

    /**
     *优化trap方法，trap方法时间复杂度太高
     * 用动态规划的思想，将每个i位置的左边的最大值和右边的最大值存下来。
     * i位置左边的最大值等于 i-1左边的最大值和height[i-1]中较大的一个，即leftMax[i] = max(leftMax[i-1], height[i-1])
     * 同理右边的最大值 rightMax[i] = max(rightMax[i+1], height[i+1])
     * @param height 高度数组
     * @return 所装的雨水量
     */
    public static int trap1(int[] height) {
        int ans = 0;
        if (height.length < 3){
            return ans;
        }
        int[] leftMax = new int[height.length];
        int[] rightMax = new int[height.length];
        leftMax[0] = height[0];
        rightMax[height.length - 1] = height[height.length - 1];
        //将每个i位置左边的最大值存下来
        for (int i = 1; i < height.length; i++) {
            leftMax[i] = Math.max(leftMax[i-1], height[i-1]);
        }
        //将每个i位置右边的最大值存下来
        for (int j = height.length - 2; j > 0; j--) {
            rightMax[j] = Math.max(rightMax[j+1], height[j+1]);
        }
        for (int i = 0; i < height.length; i++) {
            int min = Math.min(leftMax[i], rightMax[i]);
            if (min > height[i]){
                ans += min - height[i];
            }
        }
        return ans;
    }

    /**
     * 栈的应用，不直观
     * @param height 高度数组
     * @return 所装的雨水量
     */
    public static int trap2(int[] height) {
        int ans = 0, current = 0;
        Stack<Integer> st = new Stack<>();
        while (current < height.length) {
            while (!st.empty() && height[current] > height[st.peek()]) {
                int top = st.peek();
                st.pop();
                if (st.empty())
                    break;
                int distance = current - st.peek() - 1;
                int bounded_height = Math.min(height[current], height[st.peek()]) - height[top];
                ans += distance * bounded_height;
            }
            st.push(current++);
        }
        return ans;
    }

    /**
     * 找规律
     * 接完雨水，很容易看出这个数组有什么特点，就像一个山峰一样。
     * 在最大值左边不严格递增，右边不严格递减。
     * 因此只需要把原数组变成符合这样要求的数组就行了，改变的量就是接的雨水。
     *
     * 具体实现只需要先找到最大值索引，左右各自遍历一遍
     * 两边都维护一个值来表示之前的最大值以保证单调性，
     * 如果比最大值小，雨水量就加上这个差值，
     * 如果大于等于，就更新最大值。
     * @param height 高度数组
     * @return 所装的雨水量
     */
    public static int trap3(int[] height) {
        int maxHeight = 0;
        int left = 0, right = height.length - 1, ans = 0, leftMax = 0, rightMax = 0;
        if (height.length < 3){
            return ans;
        }
        for (int i = 0; i < height.length; i++) {
            if (height[maxHeight] < height[i]){
                maxHeight = i;
            }
        }
        //遍历左边
        while(left < maxHeight){
            if (leftMax < height[left]){
                leftMax = height[left];
            }else {
                ans += leftMax - height[left];
            }
            left++;
        }
        //遍历右边
        while(right > maxHeight){
            if (rightMax < height[right]){
                rightMax = height[right];
            }else {
                ans += rightMax - height[right];
            }
            right--;
        }
        return ans;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{0,1,0,2,1,0,1,3,2,1,2,1};
        System.out.println(trap3(nums));
    }
}
