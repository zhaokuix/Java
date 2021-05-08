package Stack;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.PriorityQueue;

/**
 * https://leetcode-cn.com/problems/min-stack/
 */
public class MinStack {
    /** initialize your data structure here. */
    Deque<Integer> stack;
    PriorityQueue<Integer> queue;
    public MinStack() {
        stack = new ArrayDeque<>();
        queue = new PriorityQueue<>();
    }

    public void push(int x) {
        stack.push(x);
        queue.add(x);
    }

    public void pop() {
        Integer top = stack.pop();
        queue.remove(top);
    }

    public int top() {
        return stack.getFirst();
    }

    public int getMin() {
        return queue.peek() == null ? -1:queue.peek();
    }

    public static void main(String[] args) {
        System.out.println(-4>>>1);
    }
}
