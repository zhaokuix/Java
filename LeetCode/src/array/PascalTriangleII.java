package array;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/pascals-triangle-ii/
 */
public class PascalTriangleII {
    public static List<Integer> getRow(int rowIndex) {
        rowIndex++;
        if (rowIndex < 1) return new ArrayList<>();
        if (rowIndex == 1)return  Collections.singletonList(1);
        List<Integer> last = new ArrayList<>();last.add(1);//保存上一行数据
        List<Integer> list = null;//当前行数据
        int s = 2;
        while (s <= rowIndex){
            list = new ArrayList<>();
            list.add(1);
            for (int i = 1; i < s - 1; i++) {
                list.add(last.get(i-1) + last.get(i));
            }
            list.add(1);
            last = list;
            s++;
        }
        return list;
    }

    public static void main(String[] args) {
        System.out.println(getRow(3).toString());
    }
}
