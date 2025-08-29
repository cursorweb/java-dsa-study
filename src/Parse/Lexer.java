package Parse;

import java.util.ArrayList;

public class Lexer {
    String code;
    int start = 0;
    int end = 0;
    ArrayList<String> tokens = new ArrayList<>();

    Lexer(String code) {
        this.code = code;
    }

    ArrayList<String> lex() {
        while (valid()) {
            start = end;
            char c = next();

            switch (c) {
                case '+', '*', '-', '/', '?', ':', '(', ')', '^', '!' -> addToken();
                case ' ', '\n', '\r', '\t' -> {
                }
                default -> {
                    if ('0' <= c && c <= '9') {
                        while (valid() && '0' <= peek() && peek() <= '9') {
                            next();
                        }
                        addToken();
                    } else {
                        throw new RuntimeException("yikers character: " + c);
                    }
                }
            }
        }

        tokens.add("\0");

        return tokens;
    }

    void addToken() {
        tokens.add(code.substring(start, end));
    }

    boolean valid() {
        return end < code.length();
    }

    char peek() {
        return code.charAt(end);
    }

    char next() {
        if (!valid()) return '\0';
        char prev = peek();
        end++;
        return prev;
    }

    /**
     * stupid ahh method
     * <p>
     * ```
     * if (isNumber("3")) {
     * int i = Integer.parseInt("3");
     * }
     * ```
     */
    public static boolean isNumber(String num) {
        try {
            Integer.parseInt(num);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
