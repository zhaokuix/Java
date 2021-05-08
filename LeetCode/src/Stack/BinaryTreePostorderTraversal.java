package Stack;

import java.util.*;

/**
 * https://leetcode-cn.com/problems/binary-tree-postorder-traversal/
 */
public class BinaryTreePostorderTraversal {
    /**
     * 二叉树后序遍历 左-右-中
     * @param root 根节点
     * @return 遍历顺序
     */
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        traversal(root, list);
        return list;
    }

    /**
     * 方法一、递归
     */
    public void traversal(TreeNode root, List<Integer> list){
        if (root != null){
            if (root.left != null){
                traversal(root.left, list);
            }
            if (root.right != null){
                traversal(root.right, list);
            }
            list.add(root.val);
        }
    }

    /**
     * 方法二、 栈、颜色标记
     * 未访问过的节点默认为白色，默认根节点入栈
     * 出栈时，如果当前节点为白色，那么将当前节点标记为灰色，并按照中-右-左的顺序入栈
     * 如果当前节点为灰色，将当前节点加入list中即可
     */
    public void traversal2(TreeNode root, List<Integer> list){
        if (root == null) return;
        final Integer white = 0, gray = 1;//声明颜色
        Map<TreeNode, Integer> map = new HashMap<>();//保存节点和颜色的对应关系
        Deque<TreeNode> stack = new ArrayDeque<>();//声明一个栈
        map.put(root, white);//默认为白色
        stack.push(root);//默认根节点入栈
        while(!stack.isEmpty()){
            TreeNode node = stack.pop();//出栈
            if (map.get(node).equals(white)){//如果当前节点为白色，将当前节点标记为灰色,并按照中-右-左的顺序入栈
                stack.push(node);map.put(node, gray);//将当前节点入栈并标记为灰色
                if (node.right != null){
                    stack.push(node.right);
                    map.put(node.right, white);//默认为白色
                }
                if (node.left != null){
                    stack.push(node.left);
                    map.put(node.left, white);
                }
            }else {//如果当前节点为灰色，放入list中
                list.add(node.val);
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
