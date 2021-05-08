package array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * https://leetcode.com/problems/merge-intervals/
 */
public class MergeIntervals {
    /**
     * 解答不符合题干
     * @param intervals
     * @return
     */
    public static int[][] merge(int[][] intervals) {
        List<List<Integer>> ans = new ArrayList<>();
        //数组最大长度
        int maxInt = 0;
        for (int i = 0; i < intervals.length; i++) {
            maxInt = Math.max(maxInt, intervals[i][1]);
        }
        //初始化这个数组
        int[] temp = new int[maxInt + 1];
        //遍历每个intervals
        for (int i = 0; i < intervals.length; i++) {
            int start = intervals[i][0];
            int end = intervals[i][1];
            for (int j = start; j <= end ; j++) {
                temp[j] = 1;
            }
        }
        //遍历 temp，找到第一个1作为左区间，遇到0将区间闭合,然后再找下一个1作为左区间
        List<Integer> item = new ArrayList<>(2);
        for (int i = 1; i < temp.length; i++) {
            if (temp[i-1] ==0 && temp[i] == 1){
                item.add(i);
            }
            if (i == temp.length - 1 || (temp[i-1] ==1 && temp[i] == 0)){
                if (i == temp.length - 1){
                    item.add(i);
                }else {
                    item.add(i-1);
                }
                ans.add(item);
                item = new ArrayList<>(2);
            }
        }
        int[][] ansArray = new int[ans.size()][2];
        int i = 0;
        for (List<Integer> t: ans){
            ansArray[i][0] = t.get(0);
            ansArray[i][1] = t.get(1);
            i++;
        }
        return  ansArray;
    }

    /**
     * 我们用数组 merged 存储最终的答案。
     * 首先，我们将列表中的区间按照左端点升序排序。然后我们将第一个区间加入 merged 数组中，并按顺序依次考虑之后的每个区间：
     * 如果当前区间的左端点在数组 merged 中最后一个区间的右端点之后，那么它们不会重合，我们可以直接将这个区间加入数组 merged 的末尾；
     * 否则，它们重合，我们需要用当前区间的右端点更新数组 merged 中最后一个区间的右端点，将其置为二者的较大值。
     * @param intervals
     * @return
     */
    public static int[][] merge2(int[][] intervals) {
        //对于一个已定义的二位数组a进行如下规则排序,首先按照每一个对应的一维数组第一个元素进行升序排序（即a[][0]）,
        //若第一个元素相等,则按照第二个元素进行升序排序（a[][1]）。

        //根据第一个元素排序，若第一个元素相同按照第二个元素排序
        Arrays.sort(intervals, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if (o1[0]==o2[0]) return o1[1]-o2[1];
                return o1[0]-o2[0];
            }
        });
        //根据第一个元素排序，若第一个元素相同按照第二个元素排序 lambda简写
        Arrays.sort(intervals, (o1, o2) -> {
            if (o1[0]==o2[0]) return o1[1]-o2[1];
            return o1[0]-o2[0];
        });
        //根据二维数组的第一个元素排序简写
        Arrays.sort(intervals, Comparator.comparingInt(s -> s[0]));
        if (intervals.length < 1){
            return intervals;
        }
        List<int[]> ans = new ArrayList<>();
        ans.add(intervals[0]);
        //Lambda排序
        ans = ans.stream().sorted(Comparator.comparing(s -> s[0])).collect(Collectors.toList());
        int j = 1;
        for (int i = 1; i < intervals.length; i++) {
            //当前区间的左端点在ans的右端点之后，直接加到ans中
            if (ans.get(j-1)[1] < intervals[i][0]){
                ans.add(intervals[i]);
                j++;
            }else {
                if (ans.get(j-1)[1] < intervals[i][1]){
                    ans.get(j-1)[1] = intervals[i][1];
                }
            }
        }
        int[][] merged = new int[ans.size()][2];
        for (int i = 0; i < ans.size(); i++) {
            merged[i] = ans.get(i);
        }
        return merged;
    }

    public static void main(String[] args) {
        int[][] nums = new int[][]{
                {1,3},
                {10,16},
                {8,10}
        };
        int[][] s = merge2(nums);
        for (int i = 0; i < s.length; i++) {
            System.out.println(s[i][0] + " " + s[i][1]);
        }

    }
}
