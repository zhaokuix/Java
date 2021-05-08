package array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SubsetsII {
    public static List<List<Integer>> subsetsWithDup(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> ans = new ArrayList<>();
        ans.add(new ArrayList<>());
        for (int num : nums) {
            int size = ans.size();
            for (int i = 0; i < size; i++) {
                List<Integer> list = new ArrayList<>(ans.get(i));
                list.add(num);
                if (!ans.contains(list))
                ans.add(list);
            }
        }
        return ans;
    }

    /**
     * 优化
     * 只有上一级result中的后一部分，可以加入这个重复元素后不产生结果重复。
     * 比如：对于1，2，2，3而言，当我们处理完前两个元素后，结果为[][1][2][1,2], 我们可以发现，
     * 只有[2],[1,2]再加入一个2后不会产生重复，以此类推，只有上一级后一半可以用。
     * 所以我们定义start和end来操作这个区间，如果前后两个元素相等，我们把start给到result后一半数的起始index，
     * end给到最后一个值，对于不重复的部分，我们就按照start为0，end为最后一个值即可。

     */
    public static List<List<Integer>> subsetsWithDup2(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> ans = new ArrayList<>();
        ans.add(new ArrayList<>());
        int start = 0, end = 0;
        for (int i = 0; i < nums.length; i++) {
            if (i > 0 && nums[i] == nums[i - 1]){
                start = end;
            }else {
                start = 0;
            }
            end = ans.size();
            for (int j = start; j < end; j++) {
                List<Integer> list = new ArrayList<>(ans.get(j));
                list.add(nums[i]);
                ans.add(list);
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{1,2,2};
        System.out.println(subsetsWithDup2(nums));
    }
}
