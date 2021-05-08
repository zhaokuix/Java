package array;

import java.util.Arrays;

/**
 * https://leetcode-cn.com/problems/plus-one/
 */
public class PlusOne {
    public static int[] plusOne(int[] digits) {
        int[] ans = new int[digits.length + 1];
        System.arraycopy(digits, 0, ans, 1, ans.length - 1);
        for (int i = ans.length - 1; i >= 0; i--) {
            int temp = ans[i] + 1;
            if (temp < 10){
                ans[i] = temp;
                break;
            }else {
                ans[i] = 0;
            }
        }
        if (ans[0] == 0){
            System.arraycopy(ans, 1, digits, 0, ans.length - 1);
            return digits;
        }
        return ans;
    }

    public static void main(String[] args) {
        int[] digit = new int[]{8,9,9};
        System.out.println(Arrays.toString(plusOne(digit)));
    }
}
