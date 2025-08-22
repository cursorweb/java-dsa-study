package Parser;

import java.util.ArrayList;
import java.util.HashMap;

public class PrattParser extends Parser {
    @Override
    public Expr parse(ArrayList<String> tokens) {
        return new Parser(tokens).parse();
    }

    // binding power 0 = end of input
    private static class Parser extends GenericParser {
        static class Parselet {
            final int leftBp;
            final int rightBp;

            Parselet(int l, int r) {
                leftBp = l;
                rightBp = r;
            }

            Parselet(int r) {
                leftBp = -1;
                rightBp = r;
            }
        }

        static HashMap<String, Parselet> infixParselet = new HashMap<>();
        static HashMap<String, Parselet> prefixParselet = new HashMap<>();

        static {
            // right associative
            infixParselet.put("?", new Parselet(2, 1));
            infixParselet.put("+", new Parselet(2, 3));
            infixParselet.put("-", new Parselet(2, 3));
            infixParselet.put("*", new Parselet(4, 5));
            infixParselet.put("/", new Parselet(4, 5));
            infixParselet.put("^", new Parselet(9, 8));

            prefixParselet.put("-", new Parselet(7));
        }

        ;

        public Parser(ArrayList<String> t) {
            super(t);
        }

        Expr parse() {
            return exprBp(0);
        }

        Expr exprBp(int minBp) {
            Expr left;
            if (Lexer.isNumber(peek())) {
                String leftStr = next();
                left = new Expr.Literal(leftStr);
            } else if (match("-")) {
                Parselet parselet = prefixParselet.get("-");
                assert parselet.leftBp == -1;
                Expr right = exprBp(parselet.rightBp);
                left = new Expr.Unary("-", right);
            } else if (match("(")) {
                left = new Expr.Grouping(exprBp(0)); // go back to the lowest precedence (match all)
                expect(")");
            } else {
                throw new RuntimeException("Unexpected character: " + peek());
            }

            // binary ops
            while (valid()) {
                String op = peek();

                if (!infixParselet.containsKey(op)) {
                    break;
                }

                Parselet parselet = infixParselet.get(op);
                assert parselet.leftBp != -1;
                if (parselet.leftBp < minBp) {
                    break;
                }

                next(); // eat the operator
                Expr right = exprBp(parselet.rightBp);

                // just add ternary here (it's a weird like-binary, but with an extra)
                if (op.equals("?")) {
                    expect(":");
                    // left == cond
                    // right == left
                    Expr rhs = exprBp(0); // lowest
                    left = new Expr.Ternary(left, right, rhs);
                    continue;
                }

                left = new Expr.Binary(left, op, right);
            }

            return left;

        }
    }
}
