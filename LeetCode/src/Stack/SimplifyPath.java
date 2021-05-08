package Stack;

import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

public class SimplifyPath {
    /**
     * 简化路径
     * @param path 路径
     * @return 简化后的路径
     */
    public static String simplifyPath(String path) {
        return Paths.get(path).normalize().toString();
    }

    /**
     * 简化路径
     * @param path 路径
     * @return 简化后的路径
     */
    public static String simplifyPath2(String path) {
        List<String> list = Arrays.asList("",".","..");
        Deque<String> deque = new ArrayDeque<>();
        for (String s : path.split("/")) {
            if (!list.contains(s)){
                deque.add(s);
            }else if (!deque.isEmpty() && s.equals("..")){
                deque.pollLast();
            }
        }
        return "/" + String.join("/", deque);
    }

    public static void main(String[] args) {
        String path = "/home//foo/foo////../";
        System.out.println(simplifyPath2(path));
    }
}
