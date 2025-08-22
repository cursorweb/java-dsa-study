package Parser;

import java.util.ArrayList;
import java.util.Scanner;

public abstract class Parser {
    public abstract Expr parse(ArrayList<String> tokens);

    public void run() {
        Scanner scan = new Scanner(System.in);

        while (true) {
            System.out.print("> ");
//            System.out.flush();
            String line = scan.nextLine().trim();
            if (line.isEmpty()) {
                break;
            }

            Lexer lexer = new Lexer(line);
            ArrayList<String> tokens = lexer.lex();
            Expr tree = parse(tokens);
            System.out.println(tree);
        }
    }

    public static class GenericParser {
        ArrayList<String> tokens;
        int i = 0;

        GenericParser(ArrayList<String> tokens) {
            this.tokens = tokens;
        }

        String peek() {
            return tokens.get(i);
        }

        boolean valid() {
            return !peek().equals("\0");
        }

        String next() {
            String prev = peek();

            if (valid()) {
                i++;
            }
            return prev;
        }

        String prev() {
            return tokens.get(i - 1);
        }

        boolean match(String... args) {
            for (String arg : args) {
                if (peek().equals(arg)) {
                    next();
                    return true;
                }
            }
            return false;
        }

        void expect(String token) {
            if (!match(token)) {
                throw new RuntimeException("expected: " + token + " (got " + peek() + ")");
            }
        }
    }
}
