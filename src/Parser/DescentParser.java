package Parser;

import java.util.ArrayList;

public class DescentParser {
    public static void main(String[] args) {
        Lexer lexer = new Lexer("1 + 2 * 3 ? 5 : (6+4) * 3");
        ArrayList<String> tokens = lexer.lex();
        Expr tree = new Parser(tokens).parse();
        System.out.println(tree);
    }

    private static class Parser {
        ArrayList<String> tokens;
        int i = 0;

        Parser(ArrayList<String> tokens) {
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
                throw new RuntimeException("expected: " + token);
            }
        }

        Expr parse() {
            Expr out = expr();
            expect("\0");
            return out;
        }

        Expr expr() {
            return ternary();
        }

        Expr ternary() {
            Expr out = term();
            if (match("?")) {
                Expr left = expr();
                expect(":");
                Expr right = expr();
                out = new Expr.Ternary(out, left, right);
            }
            return out;
        }

        Expr term() {
            Expr out = mult();
            while (match("+", "-")) {
                String tok = prev();
                Expr right = mult();
                out = new Expr.Binary(out, tok, right);
            }

            return out;
        }

        Expr mult() {
            Expr out = unary();
            while (match("*", "/")) {
                String tok = prev();
                Expr right = unary();
                out = new Expr.Binary(out, tok, right);
            }
            return out;
        }

        Expr unary() {
            if (match("-")) {
                String tok = prev();
                Expr right = unary();
                return new Expr.Unary(tok, right);
            }

            return primary();
        }

        Expr primary() {
            if (Lexer.isNumber(peek())) {
                return new Expr.Literal(next());
            }

            if (match("(")) {
                Expr e = expr();
                expect(")");
                return new Expr.Grouping(e);
            }

            throw new RuntimeException("Unexpected character: " + peek());
        }
    }
}
