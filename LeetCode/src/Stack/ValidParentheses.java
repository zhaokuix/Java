package Stack;

import java.util.ArrayDeque;
import java.util.Deque;

public class ValidParentheses {
    /**
     * 使用Stack
     * 根据左边把右边入栈，看是否匹配
     * @param s 字符串
     * @return 是否匹配
     */
    public static boolean isValid(String s) {
        char[] chars = s.toCharArray();
        Deque<Character> stack = new ArrayDeque<>();
        for (char c : chars) {
            if (c == '(') stack.push(')');
            else if (c == '{')stack.push('}');
            else if (c == '[')stack.push(']');
            else if (stack.isEmpty() || c != stack.pop())return false;
        }
        return stack.isEmpty();
    }

    public static void main(String[] args) {
        String s = "[({(())}[()])]";
        System.out.println(isValid(s));
    }
}
