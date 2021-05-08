package array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * https://leetcode.com/problems/insert-interval/
 */
public class InsertInterval {
    public static int[][] insert(int[][] intervals, int[] newInterval) {
        if (intervals.length == 0){
            return new int[][]{newInterval};
        }
        List<int[]> temp = new ArrayList<>();
        temp.add(newInterval);
        temp.addAll(Arrays.asList(intervals));
        //排序
        temp = temp.stream().sorted(Comparator.comparing(s -> s[0])).collect(Collectors.toList());
        List<int[]> ansList = new ArrayList<>();
        ansList.add(temp.get(0));
        //标记ansList的最后一个区间的位置
        int j = 0;
        for (int i = 1; i < temp.size(); i++) {
            //ansList的右区间小于当前的左区间,把当前区间加入ansList
            if (ansList.get(j)[1] < temp.get(i)[0]){
                ansList.add(temp.get(i));
                j++;
            }//ansList的右区间小于当前的右区间，ansList右区间扩大到当前的右区间
            else if (ansList.get(j)[1] < temp.get(i)[1]){
                ansList.get(j)[1] = temp.get(i)[1];
            }
        }
        int[][] ans = new int[ansList.size()][2];
        for (int i = 0; i < ansList.size(); i++) {
            ans[i] = ansList.get(i);
        }
        return ans;
    }

    public static void main(String[] args) {
        int[][] intervals = new int[][]{
                {1,2},
                {3,5},
                {6,7},
                {8,10},
                {12,16},
        };
        int[] newInterval = new int[]{4,8};
        int[][] ans = insert(intervals, newInterval);
        for (int[] an : ans) {
            System.out.println(Arrays.toString(an));
        }
    }
}
