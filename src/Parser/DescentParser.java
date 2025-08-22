package Parser;

import java.util.ArrayList;

public class DescentParser extends Parser {
    public Expr parse(ArrayList<String> tokens) {
        return new Parser(tokens).parse();
    }

    private static class Parser extends GenericParser {
        Parser(ArrayList<String> tokens) {
            super(tokens);
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
            Expr out = power();
            while (match("*", "/")) {
                String tok = prev();
                Expr right = power();
                out = new Expr.Binary(out, tok, right);
            }
            return out;
        }

        Expr power() {
            Expr out = unary();
            if (match("^")) {
                String tok = prev();
                Expr right = power();
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
