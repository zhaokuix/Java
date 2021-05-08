package greedy;

import java.util.Arrays;

/**
 * 从左往右发一遍，从右往左发一遍
 */
public class Candy {

    public static int candy(int[] ratings) {
        if (ratings == null || ratings.length == 0) {
            return 0;
        }
        int[] caddy = new int[ratings.length];
        caddy[0] = 1;
        //从左往右看，如果后一位比前一位分高，就给比前一位多1的糖果，否则给1
        for (int i = 1; i < ratings.length; i++) {
            if (ratings[i] > ratings[i - 1]){
                caddy[i] = caddy[i-1] + 1;
            }else {
                caddy[i] = 1;
            }
        }
        //从右往左看，如果前一位比后一位分高，并且前一位的糖果小于或等于后一位，就给前一位的糖果比后一位加1
        for (int i = ratings.length - 2; i >= 0; i--) {
            if (ratings[i] > ratings[i+1] && caddy[i] <= caddy[i+1]){
                caddy[i] = caddy[i+1] + 1;
            }
        }
        int ans = 0;
        for (int i : caddy) {
            ans += i;
        }
        return ans;
    }

    public static void main(String[] args) {
//        int[] rating = {1,3,2,2,1};
//        System.out.println(candy(rating));
        System.out.println(Arrays.toString("or mike".split("or")));
    }
}
