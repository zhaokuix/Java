import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Test {
    public static void main(String[] args) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("123456");
        stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length());
        System.out.println(stringBuilder);
    }
}
