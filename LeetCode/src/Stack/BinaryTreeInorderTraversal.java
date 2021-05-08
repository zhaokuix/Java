package Stack;

import java.util.*;

/**
 * https://leetcode-cn.com/problems/binary-tree-inorder-traversal/
 */
public class BinaryTreeInorderTraversal {
    /**
     * 二叉树中序遍历
     * @param root 二叉树
     * @return 中序遍历
     */
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        traversal2(root, list);
        return list;
    }
    //中序遍历，递归
    public void traversal(TreeNode root, List<Integer> list){
        if (root.left != null){
            traversal(root.left, list);
        }
        list.add(root.val);
        if (root.right != null) {
            traversal(root.right, list);
        }

    }

    /**
     *中序遍历颜色标记
     *使用颜色标记节点的状态，新节点为白色，已访问的节点为灰色。
     *如果遇到的节点为白色，则将其标记为灰色，然后将其右子节点、自身、左子节点依次入栈
     *如果遇到的节点为灰色，则将节点的值输出。
     * @param root 根节点
     * @param list 链表
     */
    public void traversal2(TreeNode root, List<Integer> list){
        final Integer white = 0, gray = 1;
        Map<TreeNode, Integer> map = new HashMap<>();
        Deque<TreeNode> deque = new ArrayDeque<>();
        map.put(root, white);
        deque.push(root);
        while(!deque.isEmpty()){
            TreeNode node = deque.pop();
            if (map.get(node).equals(white)){
                if (node.right != null) {
                    deque.push(node.right);
                    map.put(node.right, white);
                }
                deque.push(node);map.put(node, gray);//遇到的节点为白色，则将其标记为灰色
                if (node.left != null){
                    deque.push(node.left);
                    map.put(node.left, white);
                }
            }else {
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
