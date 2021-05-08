package Stack;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/binary-search-tree-iterator/
 */
public class BinarySearchTreeIterator {
    List<Integer> list;
    Integer index;
    /**
     *二叉搜索树的中序遍历时按照顺序来的，可以用一个链表来存储它，但是这个思路不符合空间复杂度
     * (O(h) 内存，其中 h 是树的高度)
     * 的要求
     */
    public BinarySearchTreeIterator(TreeNode root) {
        this.list = new ArrayList<>();
        this.traversal(root, list);
        this.index = -1;
    }
    /**
     * 中序遍历 左-中-右
     */
    public void traversal(TreeNode root, List<Integer> list){
        if (root != null){
            if (root.left != null) traversal(root.left, list);
            list.add(root.val);
            if (root.right != null) traversal(root.right, list);
        }
    }

    /** @return the next smallest number */
    public int next() {
        return list.get(++index);
    }

    /** @return whether we have a next smallest number */
    public boolean hasNext() {
        return index + 1 < list.size();
    }

     public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }


    /**
     *受控的递归
     * 将给定节点中的所有左子节点添加到栈中，直到节点没有左子节点为止
     * 如果栈顶节点没有右节点，说明是叶子节点，即最小元素
     * 如果栈顶节点右右节点，那么再把右节点的所有左节点放入栈中，依次递归
     *
     * hasNext()：若栈中还有元素，则返回 true，反之返回 false。所以这是一个 O(1) 的操作。
     * next()：包含了两个主要步骤。一个是从栈中弹出一个元素，它是下一个最小的元素。这是一个 O(1) 的操作。
     * 然而，随后我们要调用帮助函数 _inorder_left ，它需要递归的，将左节点添加到栈上，是线性时间的操作，
     * 最坏的情况下为 O(N)。但是我们只对含有右节点的节点进行调用，它也不会总是处理 N 个节点。
     * 只有当我们有一个倾斜的树，才会有 N 个节点。因此该操作的平均时间复杂度仍然是 O(1)，符合问题中所要求的。
     * 空间复杂度：O(h)，使用了一个栈来模拟递归。
     *
     */
    static class BSTIterator {

        Deque<TreeNode> stack = new ArrayDeque<>();

        public BSTIterator(TreeNode root) {
            this.traversal(root);
        }
        /**
         * 将所有左节点放入stack中
         */
        public void traversal(TreeNode root){
            while (root != null){
                this.stack.push(root);
                root = root.left;
            }
        }

        /** @return the next smallest number */
        public int next() {
            TreeNode node = stack.pop();
            if (node.right != null){
                traversal(node.right);
            }
            return node.val;
        }

        /** @return whether we have a next smallest number */
        public boolean hasNext() {
            return !stack.isEmpty();
        }
    }
}
