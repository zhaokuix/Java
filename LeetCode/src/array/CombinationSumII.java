package array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * https://leetcode.com/problems/combination-sum-ii/
 * 回溯法
 */
public class CombinationSumII {
    public static List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> list  = new ArrayList<>();
        Arrays.sort(candidates);
        process(0, candidates, target, list ,result);
        return result;
    }
    public static void process(int start, int[] candidates, int target, List<Integer> list, List<List<Integer>> result){
        if (target < 0){
            return;
        }
        if (target == 0){
            result.add(new ArrayList<>(list));
        }
        for (int i = start; i < candidates.length; i++) {
            //提高效率
            if(i > start && candidates[i] == candidates[i-1])
            {
                continue;
            }
            if (target - candidates[i] < 0){
                break;
            }
            list.add(candidates[i]);
            //每个数只允许使用一次，因此要传入i的下一个值
            process(i + 1, candidates, target - candidates[i], list, result);
            list.remove(list.size() - 1);
        }
    }

    public static void main(String[] args) {
        int[] nums = new int[]{2,5,2,1,2};
        System.out.println(combinationSum2(nums, 5));
    }
}
