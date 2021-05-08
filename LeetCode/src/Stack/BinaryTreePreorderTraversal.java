package Stack;

import java.util.*;

/**
 * https://leetcode-cn.com/problems/binary-tree-preorder-traversal/
 */
public class BinaryTreePreorderTraversal {
    /**
     * 二叉树前序遍历 中-左-右
     * @param root
     * @return
     */
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        traversal(root, list);
        return list;
    }
    /**
     * 方法一、递归
     */
    public void traversal(TreeNode root, List<Integer> list){
        if (root != null) {
            list.add(root.val);
            if (root.left != null) traversal(root.left, list);
            if (root.right != null) traversal(root.right, list);
        }
    }

    /**
     * 方法二、颜色标记，没遍历过的地方默认为白色，如果当前节点为白色，那么将当前节点标记为灰色，并按照右、左、中入栈
     * 出栈时如果是灰色就放入list中,如果当前节点为白色，那么将当前节点标记为灰色，并按照右、左、中入栈
     */
    public void traversal2(TreeNode root, List<Integer> list){
        if (root == null) return;
        final Integer white = 0, gray = 1;//标记颜色
        Map<TreeNode, Integer> map = new HashMap<>();//标记node的颜色
        Deque<TreeNode> stack = new ArrayDeque<>();//声明一个栈
        map.put(root, white);
        stack.push(root);
        while(!stack.isEmpty()){
            TreeNode node = stack.pop();//出栈
            if (map.get(node).equals(gray)){//如果节点为灰色，放入list中
                list.add(node.val);
            }else {//如果当前节点为白色
                if (node.right != null){
                    stack.push(node.right);
                    map.put(node.right, white);//默认为白色
                }
                if (node.left != null){
                    stack.push(node.left);
                    map.put(node.left, white);
                }
                stack.push(node);
                map.put(node, gray);//当前节点为白色，将当前节点标记为灰色
            }
        }
    }
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }
}
