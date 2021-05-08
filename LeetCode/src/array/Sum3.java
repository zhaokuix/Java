package array;

import java.util.*;
import java.util.stream.Collectors;

/**
 * https://leetcode.com/problems/3sum/
 */
public class Sum3 {

    /**
     * 方法一、暴力遍历***********超时了
     * @param nums 数组
     * @return list集合
     */
    public static List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> target = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                for (int k = j + 1; k < nums.length; k++) {
                    if (nums[i] + nums[j] + nums[k] == 0){
                        List<Integer> list = Arrays.asList(nums[i], nums[j], nums[k]);
                        Collections.sort(list);
                        boolean bool = false;
                        for(List<Integer> t : target){
                            if (t.toString().equals(list.toString())) bool = true;
                        }
                        if (!bool){
                            target.add(list);
                        }
                    }
                }
            }
        }
        return target;
    }

    /**
     * 方法二、尝试优化***********超时了
     * @param nums 数组
     * @return list集合
     */
    public static List<List<Integer>> threeSum2(int[] nums) {
        Set<List<Integer>> target = new HashSet<>();
        Arrays.sort(nums);
        for (int i = 0; i < nums.length && nums[i] <= 0; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                for (int k = nums.length - 1; k > j; k--) {
                    if (nums[i] + nums[j] + nums[k] == 0) {
                        List<Integer> list = Arrays.asList(nums[i], nums[j], nums[k]);
                        target.add(list);
                        break;
                    }
                }
            }
        }
        return new ArrayList<>(target);
    }

    /**
     * 方法三、双指针
     * @param nums 数组
     * @return list集合
     */
    public static List<List<Integer>> threeSum3(int[] nums) {
        Set<List<Integer>> target = new HashSet<>();
        Arrays.sort(nums);
        for (int i = 0; i < nums.length - 2 && nums[i] <= 0; i++) {
            //左指针
            int l = i + 1;
            //右指针
            int r = nums.length - 1;
            while (l < r){
                int sum = nums[i] + nums[l] + nums[r];
                if (sum == 0){
                    List<Integer> list = Arrays.asList(nums[i], nums[l], nums[r]);
                    target.add(list);
                    l++ ;
                    r--;
                }else if (sum > 0){
                    r--;
                }else {
                    l++;
                }
            }
        }
        return new ArrayList<>(target);
    }

    /**
     * 方法四、跳出程序思维，基于本题，分情况讨论，要满足条件只有三种情况(结果超时，还是需要使用双指针才能满足时间要求)❌
     * @param nums 数组
     * @return list集合
     */
    public static List<List<Integer>> threeSum4(int[] nums) {
        Set<List<Integer>> target = new HashSet<>();
        List<Integer> numsList = new ArrayList<>();
        Arrays.sort(nums);
        for (int i : nums)numsList.add(i);
        //[x, x, x]
        if (Collections.frequency(numsList,0) > 2){
            target.add(Arrays.asList(0, 0, 0));
        }
        //[x, x, -2x]
        for(Integer num : numsList){
            if (Collections.frequency(numsList, num) > 1 && num != 0 && numsList.contains(-2*num)){
                target.add(Arrays.asList(num, num, -2*num));
            }
        }
        //[x, y, -y-x]
        Set<Integer> set = new HashSet<>(numsList);
        numsList = new ArrayList<>(set);
        Collections.sort(numsList);
        for(int i = 0; i < numsList.size() - 1; i++){
            for (int j = i + 1; j < numsList.size(); j++){
                int num = -1*numsList.get(i) - numsList.get(j);
                if (num <= numsList.get(j)){
                    break;
                }
                if (numsList.contains(num)){
                    target.add(Arrays.asList(numsList.get(i), numsList.get(j), num));
                }
            }
        }
        return new ArrayList<>(target);
    }

    public static void main(String[] args) {
        int[] nums = new int[]{0,-4,-1,-4,-2,-3,2};
        System.out.println(threeSum4(nums));
    }

}
