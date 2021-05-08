package array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * https://leetcode.com/problems/combination-sum/
 *回溯法、深度优先搜索
 * result = []
 * def backtrack(路径, 选择列表):
 *     if 满足结束条件:
 *         result.add(路径)
 *         return
 *
 * for 选择 in 选择列表:
 *     做选择
 *     backtrack(路径, 选择列表)
 *     撤销选择
 */
public class CombinationSum {
    static List<List<Integer>>  result = new ArrayList<>();
    public static List<List<Integer>> combinationSum(int[] candidates, int target) {
        if (candidates == null || candidates.length == 0 || target < 0) {
            return result;
        }
        List<Integer> list = new ArrayList<>();
        Arrays.sort(candidates);
        process(0, candidates, target, list);
        return result;
    }

    public static void process(int start, int[] candidates, int target, List<Integer> list){
        if (target < 0){
            return;
        }
        if (target == 0){
            result.add(new ArrayList<>(list));
        }
        //因为每次递归我们都是从 0 开始，所有数字都遍历一遍。所以会出现重复的组合。
        //改进一下，只需加一个 start 变量从自己开始选择。
        for (int i = start; i < candidates.length; i++) {
            // 在数组有序的前提下，剪枝
            if (target - candidates[i] < 0) {
                break;
            }
            list.add(candidates[i]);
            //因为每个数字都可以使用无数次，所以递归还可以从当前元素开始
            process(i, candidates, target - candidates[i], list);
            //因为Java是引用的，所以无论能否找到删掉这次加的数，即撤销选择
            list.remove(list.size() -1);
        }
    }

    public static void main(String[] args) {
        int[] nums = new int[]{2,6,3,7};
        System.out.println(combinationSum(nums, 7).toString());
    }
}
