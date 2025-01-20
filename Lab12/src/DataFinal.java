import java.util.*;

public class DataFinal {

    public static void main(String[] args) {



    }

    public static boolean isMatching(String expression) {

        Stack<Character> deneme = new Stack<>();
        for (char ch : expression .toCharArray()) {
            if (ch == '(' || ch == '{' || ch == '[') {
                deneme.push(ch);
            } else if (ch == ')' || ch == '}' || ch == ']') {
                if (deneme.isEmpty()) {
                    return false;
                }
                char open = deneme.pop();
                if ((ch == ')' && open != '(') ||
                        (ch == '}' && open != '{') ||
                        (ch == ']' && open != '[')) {
                    return false;
                }
            }
        }
        return deneme.isEmpty();

    }

    public static String reverse(String str) {

        Stack<Character> deneme = new Stack<>();
        for (char ch : str.toCharArray()) {
            deneme.push(ch);
        }
        StringBuilder reversed = new StringBuilder();
        while (!deneme.isEmpty()) {
            reversed.append(deneme.pop());
        }
        return reversed.toString();
    }





}
