package array;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/subsets/
 */
public class Subsets {
    /**
     * 集合的每个元素，都有可以选或不选，用二进制和位运算，可以很好的表示。
     * 假设nums=[1,2,3,4]，二进制的0可以写成0000，代表一个数也不取，1=0001表示去第一个数也就是[1]，
     * 2=0010，表示取第二个数[2]，
     * 3=0011表示取1和2位[1,2]，4=0100表示[3]....15=1111表示[1,2,3,4]
     * @param nums 数组
     * @return List<List<Integer>>
     */
    public static List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        //1<<nums.length 等价于 2^nums.length,对于n个数字的数组，共2^n个子集
        for (int i = 0; i < (1 << nums.length); i++) {
            List<Integer> sub = new ArrayList<Integer>();
            for (int j = 0; j < nums.length; j++)
//             *以0~(2^n)-1，2^n个n位二进制数，对应于所有子集，从后往前第 j 个二进制位为 0 表示不放入第 j 个元素,同理1表示放入。
//             " ((i >> j) & 1) == 1 " 表示对于二进制数i，从低位到高位逐个取其二进制位，并判断是否为1，若为1则放入对于nums中的元素。
                if (((i >> j) & 1) == 1) sub.add(nums[j]);
            res.add(sub);
        }
        return res;
    }

    /**
     * 逐个枚举，空集的幂集只有空集，每增加一个元素，让之前幂集中的每个集合，追加这个元素，就是新增的子集。
     * @param nums 数组
     * @return List<List<Integer>>
     */
    public static List<List<Integer>> subsets2(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        res.add(new ArrayList<>());
        for (int num : nums) {
            int all = res.size();
            for (int j = 0; j < all; j++) {
                List<Integer> tmp = new ArrayList<>(res.get(j));
                tmp.add(num);
                res.add(tmp);
            }
        }
        return res;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{1,2,3};
        System.out.println(subsets2(nums).toString());
    }
}
