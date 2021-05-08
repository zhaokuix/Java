package Stack;

import java.util.*;

/**
 * https://leetcode-cn.com/problems/binary-tree-zigzag-level-order-traversal/
 */
public class BinaryTreeZigzagLevelOrderTraversal {
    /**
     * 二叉树锯齿形层次遍历
     *用
     * @param root 根节点
     * @return 遍历顺序
     */
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) return res;
        //层次遍历需要用到的队列,利用双端队列进行锯齿形遍历，奇数层用push,偶数层用add
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        int level = 1;//确定奇偶层
        while (!queue.isEmpty()){
            //size控制循环次数
            int size = queue.size();
            LinkedList<Integer> list = new LinkedList<>();//LinkedList继承了List和Deque，同时拥有add和push方法
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                assert node != null;
                if ((level&1) == 1) list.add(node.val);
                else list.push(node.val);
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
            res.add(list);
            level++;
        }
        return res;
    }

    /**
     * 二叉树层次遍历，广度优先遍历（非锯齿形）
     * 队列：Queue<TreeNode> queue=new LinkedList<>();
     * 入队：add
     * 出队：poll
     * 得到队头：peek
     * 大小：size
     * 空：isEmpty
     */
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) return res;
        //层次遍历需要用到的队列
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.add(root);
        while (!queue.isEmpty()){
            //size控制循环次数
            int size = queue.size();
            List<Integer> list = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                assert node != null;
                list.add(node.val);
                if (node.left != null) queue.add(node.left);
                if (node.right != null) queue.add(node.right);
            }
            res.add(list);
        }
        return res;
    }
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x;}
    }
}
