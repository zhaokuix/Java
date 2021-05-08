package array;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/pascals-triangle/
 */
public class PascalTriangle {
    public static List<List<Integer>> generate(int numRows) {
        List<List<Integer>> ans = new ArrayList<>();
        if (numRows < 1) return ans;
        ans.add(Collections.singletonList(1));
        if (numRows == 1)return  ans;
        int s = 2;
        while (s <= numRows){
            List<Integer> last = ans.get(ans.size() - 1);
            List<Integer> list = new ArrayList<>();
            list.add(1);
            for (int i = 1; i < s - 1; i++) {
                list.add(last.get(i-1) + last.get(i));
            }
            list.add(1);
            ans.add(list);
            s++;
        }
        return ans;
    }

    public static void main(String[] args) {
        System.out.println(generate(5).toString());
    }
}
