package Stack;

import java.util.*;
import java.util.stream.Collectors;

/**
 * https://leetcode-cn.com/problems/merge-k-sorted-lists/submissions/
 */
public class MergeKSortedLists {

    /**
     * 排序法 时间复杂度O(nlog(n))
     * @param lists k个链表
     * @return 排序的链表
     */
    public ListNode mergeKLists(ListNode[] lists) {
        boolean flag = true;
        for (ListNode list : lists) {
            if (list != null){
                flag = false;
                break;
            }
        }
        if (lists.length == 0 || flag) return null;
        List<Integer> list = new ArrayList<>();
        for (ListNode listNode : lists) {
            if (listNode != null){
                list.add(listNode.val);
                while (listNode.next != null) {
                    listNode = listNode.next;
                    list.add(listNode.val);
                }
            }
        }
        list = list.stream().sorted().collect(Collectors.toList());
        ListNode head = new ListNode(list.get(0));
        ListNode tmp = head;
        for (int i = 1; i < list.size(); i++) {
            tmp.next = new ListNode(list.get(i));
            tmp = tmp.next;
        }
        return head;
    }

    /**
     * 逐条合并，比较两条链表找到最小的头节点，插入链表中
     * 时间复杂度O(k方*n)
     * @param lists k个链表
     * @return 排序的链表
     */
    public static ListNode mergeKLists2(ListNode[] lists) {
        ListNode res = null;
        for (ListNode listB : lists) {
            res = merge2Lists2(res, listB);
        }
        return res;
    }

    /**
     *两个链表合并，迭代
     */
    public static ListNode merge2Lists2(ListNode listA, ListNode listB){
        //tail 来记录下一个插入位置的前一个位置，以及两个指针 p1 和 p2 来记录 链表a 和 链表b 未合并部分的第一位
        ListNode head = new ListNode(0);
        ListNode tail = head, p1 = listA, p2 = listB;
        while (p2 != null && p1 != null){
            if (p1.val > p2.val){
                tail.next = p2;
                tail = tail.next;
                p2 = p2.next;
            }else {
                tail.next = p1;
                tail = tail.next;
                p1 = p1.next;
            }
        }
        if (p1 == null && p2 != null){
            tail.next = p2;
        }
        if (p2 == null && p1 != null){
            tail.next = p1;
        }
        return head.next;
    }

    /**
     *两个链表合并，递归
     */
    public static ListNode merge2Lists(ListNode listA, ListNode listB){
        if (listA == null){
            return listB;
        }
        if (listB == null){
            return listA;
        }
        if (listA.val < listB.val){
            listA.next = merge2Lists(listA.next, listB);
            return listA;
        }else {
            listB.next = merge2Lists(listA, listB.next);
            return listB;
        }
    }
    /**
     * 两两合并，迭代，当数组中只剩一条链表时，说明合并完成了
     * @param lists k个链表
     * @return 排序的链表
     */
    public static ListNode mergeKLists3(ListNode[] lists) {
        if (lists.length == 0) return null;
        int size = lists.length;
        while (size > 1){
            int sizeIndex = 0;
            for (int i = 0; i < size; i += 2) {
                if (i == size - 1){
                    lists[sizeIndex++] = lists[i];
                }else {
                    lists[sizeIndex++] = merge2Lists2(lists[i], lists[i + 1]);//两两合并
                }
            }
            size = sizeIndex;
        }
        return lists[0];
    }

    /**
     * 使用小顶堆优化，k个链表一起合并，将k个链首数字放进优先队列，优先队列第一个出来的一定是最小值，放到链表里即可
     * 时间复杂度O(k方*n)
     * @param lists k个链表
     * @return 排序的链表
     */
    public static ListNode mergeKLists4(ListNode[] lists) {
        PriorityQueue<ListNode> queue = new PriorityQueue<>((o1, o2) -> o1.val - o2.val);
        for (ListNode node : lists) { //四个首节点放入优先队列中
            if (node != null){
                queue.offer(node);
            }
        }
        ListNode head = new ListNode(0);
        ListNode tail = head;
        while (!queue.isEmpty()){
            tail.next = queue.poll();//优先队列的首节点一定是最小值，直接放入链表中即可
            tail = tail.next;
            if (tail.next != null){//如果最小值有下一个节点，将下一个节点放入优先队列中
                queue.offer(tail.next);
            }
        }
        return head.next;
    }

    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    public static void main(String[] args) {
        ListNode node = new ListNode(1);
        ListNode node2 = new ListNode(-1);
        ListNode[] nodes = {null, node, null, node2};
        ListNode ans = mergeKLists2(nodes);
        System.out.println(ans);
    }
}
