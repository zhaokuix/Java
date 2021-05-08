package array;

/**
 * https://leetcode.com/problems/maximum-subarray/
 * 最大子序和
 */
public class MaximumSubarray {
    /**
     * 暴力遍历
     * @param nums 数组
     * @return 最大子序和
     */
    public static int maxSubArray(int[] nums) {
        int ans = 0;
        int tmp;
        for (int i = 0; i < nums.length; i++) {
            tmp = 0;
            for (int j = i; j < nums.length; j++) {
                tmp += nums[j];
                ans = Math.max(tmp, ans);
            }
        }
        return ans;
    }

    /**
     * 动态规划，计算第 i 个数结尾的连续子数组的最大和,
     * 因此我们只需要求出每个位置的 f(i)，然后返回 f 数组中的最大值即可。
     * 时间复杂度：O(n)，空间复杂度：O(1)
     */
    public static int maxSubArray2(int[] nums) {
        int ans = nums[0];
        //pre保存i位置的最大子序列和
        int pre = 0;
        for (int num : nums) {
            pre = Math.max(num, pre + num);
            ans = Math.max(pre, ans);
        }
        return ans;
    }

    /**
     * 线段树求解 LCIS 问题，可以用于解决任意的子区间 [l, r] 的问题
     * 给定一个整数数组 nums ，找到一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。 注意越界,连个范围都没有还行，注意子数组连续。
     * @param nums 整数数组
     * @return 最少包含一个元素的最大和的连续子数组的和
     */
    public int maxSubArray3(int[] nums) {
        // 输入校验
        if (nums == null || nums.length <= 0)
            return 0;
        int len = nums.length;// 获取输入长度
        return getInfo(nums, 0, len - 1).mSum;
    }

    class wtevTree {
        int lSum;// 以左区间为端点的最大子段和
        int rSum;// 以右区间为端点的最大子段和
        int iSum;// 区间所有数的和
        int mSum;// 该区间的最大子段和

        // 构造函数
        wtevTree(int l, int r, int i, int m) {
            lSum = l;
            rSum = r;
            iSum = i;
            mSum = m;
        }
    }

    // 通过既有的属性，计算上一层的属性,一步步往上返回,获得线段树
    wtevTree pushUp(wtevTree leftT, wtevTree rightT) {
        // 新子段的lSum等于左区间的lSum或者左区间的 区间和 加上右区间的lSum
        int l = Math.max(leftT.lSum, leftT.iSum + rightT.lSum);
        // 新子段的rSum等于右区间的rSum或者右区间的 区间和 加上左区间的rSum
        int r = Math.max(leftT.rSum + rightT.iSum, rightT.rSum);
        // 新子段的区间和等于左右区间的区间和之和
        int i = leftT.iSum + rightT.iSum;
        // 新子段的最大子段和，其子段有可能穿过左右区间，或左区间，或右区间
        int m = Math.max(leftT.rSum + rightT.lSum, Math.max(leftT.mSum, rightT.mSum));
        return new wtevTree(l, r, i, m);
    }

    // 递归建立和获得输入区间所有子段的结构
    wtevTree getInfo(int[] nums, int left, int right) {
        // 若区间长度为1，其四个子段均为其值
        if (left == right)
            return new wtevTree(nums[left], nums[left], nums[left], nums[left]);
        int mid = (left + right) >> 1;// 获得中间点mid
        wtevTree leftT = getInfo(nums, left, mid);
        wtevTree rightT = getInfo(nums, mid + 1, right);//mid+1,左右区间没有交集。
        return pushUp(leftT, rightT);
    }

    public static void main(String[] args) {
        int[] nums = new int[]{-2,1,-3,4,-1,2,1,-5,4};
        System.out.println(maxSubArray(nums));
    }
}
