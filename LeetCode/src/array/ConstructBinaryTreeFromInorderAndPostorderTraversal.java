package array;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConstructBinaryTreeFromInorderAndPostorderTraversal {
    private static Map<Integer, Integer> indexMap;//存储中序遍历的节点和下标对应关系
    /**
     * 根据一棵树的后序遍历与中序遍历构造二叉树。
     */
    public static TreeNode buildTree(int[] postorder, int[] inorder) {
        //构造哈希映射快速定位节点
        indexMap = new ConcurrentHashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            indexMap.put(inorder[i], i);
        }
        return myBuildTree(postorder, inorder, 0, postorder.length - 1, 0, inorder.length - 1);
    }
    //递归构建二叉树
    public static TreeNode myBuildTree(int[] postorder, int[] inorder, int postorder_left, int postorder_right, int inorder_left, int inorder_right) {
        if (postorder_left > postorder_right) {
            return null;
        }
        // 后序遍历中的最后一个节点就是根节点
        int postorder_root = postorder_right;
        // 在中序遍历中定位根节点
        int inorder_root = indexMap.get(postorder[postorder_root]);
        //建立根节点
        TreeNode root = new TreeNode(postorder[postorder_root]);
        //得到左子树的节点数目
        int size_left_subtree = inorder_root - inorder_left;
        //递归地构造左子树，并连接到根节点
        // 后序遍历中「从 左边界 开始的 size_left_subtree」个元素就对应了中序遍历中「从 左边界 开始到 根节点定位-1」的元素
        root.left = myBuildTree(postorder, inorder, postorder_left, postorder_left + size_left_subtree - 1, inorder_left, inorder_root -1);
        // 递归地构造右子树，并连接到根节点
        // 先序遍历中「从 左边界+左子树节点数目 开始到 右边界-1」的元素就对应了中序遍历中「从 根节点定位+1 到 右边界」的元素
        root.right = myBuildTree(postorder, inorder, postorder_left + size_left_subtree, postorder_right - 1, inorder_root + 1, inorder_right);
        return root;
    }
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    public static void main(String[] args) {
        int[] inorder = new int[]{9,3,15,20,7};
        int[] postorder = new int[]{9,15,7,20,3};
        TreeNode treeNode = buildTree(postorder, inorder);
        System.out.println(treeNode);
    }
}
