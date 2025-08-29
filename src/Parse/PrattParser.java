package Parse;

import java.util.ArrayList;
import java.util.HashMap;

public class PrattParser extends BaseParser {
    @Override
    public Expr parse(ArrayList<String> tokens) {
        return new Pratt(tokens).parse();
    }


    public static class Pratt extends GenericParser {
        Pratt(ArrayList<String> tokens) {
            super(tokens);
        }

        private enum Prec {
            NONE, // 0 (just the breakpoint)
            CONDITIONAL,
            SUM,
            PRODUCT,
            EXPONENT,
            PREFIX,
            POSTFIX,
        }

        /**
         * These guys don't need a getPrec because they *set* the precedence,
         * they tell you how much more to parse, unlike infix, which needs to know left vs right
         * <br />
         * They only parse one expression, so unlike infix, they don't *need* to know when to stop
         */
        private interface PrefixParselet {
            /**
             * @param parser To do more parsing of expressions
             * @param token  The first token that was matched (e.g. '-' for '-5')
             */
            Expr parse(Pratt parser, String token);
        }

        /**
         * numbers are prefixes, like '-'!
         * They have the highest precedence... probably? So, no need to care about them.
         */
        private class NumberParselet implements PrefixParselet {
            @Override
            public Expr parse(Pratt parser, String token) {
                return new Expr.Literal(token);
            }
        }

        private class PrefixOpParselet implements PrefixParselet {
            int prec;

            public PrefixOpParselet(Prec p) {
                prec = p.ordinal();
            }

            @Override
            public Expr parse(Pratt parser, String token) {
                Expr right = parser.parseExpr(prec);
                return new Expr.Unary(token, right);
            }
        }

        private class GroupingParselet implements PrefixParselet {
            @Override
            public Expr parse(Pratt parser, String token) {
                Expr right = parser.parseExpr(0); // lowest prec
                parser.expect(")");
                return new Expr.Grouping(right);
            }
        }

        /**
         * Essentially anything but prefix (infix, postfix, mixfix)
         */
        private interface InfixParselet {
            Expr parse(Pratt parser, Expr left, String token);

            int getPrecedence();
        }

        private class BinaryOpParselet implements InfixParselet {
            private final int prec;
            private final boolean isRight;

            public BinaryOpParselet(Prec p) {
                this(p, false);
            }

            public BinaryOpParselet(Prec p, boolean r) {
                prec = p.ordinal();
                isRight = r;
            }

            @Override
            public Expr parse(Pratt parser, Expr left, String token) {
                // lower precedence for right aligning, because then:
                // 5 ^ (3 ^ 2), it *keeps* parsing 3 ^ 2 and doesn't stop at the 3
                // (which normally would have same precedence and therefore stop, since prec < nextPrec())
                Expr right = parser.parseExpr(isRight ? (prec - 1) : prec);
                return new Expr.Binary(left, token, right);
            }

            @Override
            public int getPrecedence() {
                return prec;
            }
        }

        private class PostfixParselet implements InfixParselet {
            int prec;

            public PostfixParselet(Prec p) {
                prec = p.ordinal();
            }

            @Override
            public Expr parse(Pratt parser, Expr left, String token) {
                return new Expr.Postfix(left);
            }

            @Override
            public int getPrecedence() {
                return prec;
            }
        }

        private class TernaryParselet implements InfixParselet {
            @Override
            public Expr parse(Pratt parser, Expr left, String token) {
                Expr thenArm = parser.parseExpr(0); // like a grouping, the lowest
                parser.expect(":");
                Expr elseArm = parser.parseExpr(0); // just one lower, so right associative
                // this makes sense because it also lets you use ternary again
                return new Expr.Ternary(left, thenArm, elseArm);
            }

            @Override
            public int getPrecedence() {
                return Prec.CONDITIONAL.ordinal();
            }
        }

        private static final HashMap<String, PrefixParselet> prefixParselets = new HashMap<>();
        private static final HashMap<String, InfixParselet> infixParselets = new HashMap<>();

        {
            prefixParselets.put("-", new PrefixOpParselet(Prec.PREFIX));
            prefixParselets.put("(", new GroupingParselet());
        }

        {
            infixParselets.put("+", new BinaryOpParselet(Prec.SUM));
            infixParselets.put("-", new BinaryOpParselet(Prec.SUM));
            infixParselets.put("*", new BinaryOpParselet(Prec.PRODUCT));
            infixParselets.put("/", new BinaryOpParselet(Prec.PRODUCT));

            infixParselets.put("^", new BinaryOpParselet(Prec.EXPONENT, true));

            infixParselets.put("?", new TernaryParselet());

            infixParselets.put("!", new PostfixParselet(Prec.POSTFIX));
        }

        Expr parse() {
            return parseExpr(0);
        }

        Expr parseExpr(int prec) {
            String token = next();
            PrefixParselet prefixParselet = prefixParselets.get(token);
            Expr left;
            if (Lexer.isNumber(token)) {
                left = new NumberParselet().parse(this, token);
            } else if (prefixParselet != null) {
                left = prefixParselet.parse(this, token);
            } else {
                throw new RuntimeException("Unexpected token '" + token + '"');
            }

            while (prec < getPrec()) {
                token = next();

                // don't report error here because it could just be another infix (-5 it stops at 5, and then runs prefixParselet again)
                InfixParselet infixParselet = infixParselets.get(token);
                left = infixParselet.parse(this, left, token);
            }

            return left;
        }

        int getPrec() {
            InfixParselet parselet = infixParselets.get(peek());

            if (parselet == null) {
                // if it's 0 it stops immediately
                // everyone else is 1+
                return 0;
            }

            return parselet.getPrecedence();
        }

    }
}
