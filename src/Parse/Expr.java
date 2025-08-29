package Parse;

/**
 * ```
 * expr = ternary
 * ternary = term ( "?" expr ":" expr )?
 * term = mult ( [+-] mult )*
 * mult = power ( [* /] power )*
 * power = unary ( "^" power )?             # this one is *right associative* because it expects a power
 * unary = ( -unary ) | primary
 * primary = number | "(" expr ")"
 * ```
 */
public abstract class Expr {
    public static class Binary extends Expr {
        public Expr left;
        public Expr right;
        public String op;

        public Binary(Expr l, String o, Expr r) {
            op = o;
            left = l;
            right = r;
        }

        @Override
        public String toString() {
            return "(" + op + " " + left + " " + right + ")";
        }
    }

    public static class Literal extends Expr {
        public int value;

        public Literal(String s) {
            value = Integer.parseInt(s);
        }

        @Override
        public String toString() {
            return "" + value;
        }
    }

    public static class Grouping extends Expr {
        public Expr expr;

        public Grouping(Expr e) {
            expr = e;
        }

        @Override
        public String toString() {
            return "(group " + expr + ")";
        }
    }

    public static class Unary extends Expr {
        public String op;
        public Expr right;

        public Unary(String o, Expr r) {
            op = o;
            right = r;
        }

        @Override
        public String toString() {
            return "(" + op + right + ")";
        }
    }

    public static class Postfix extends Expr {
        public Expr left;

        public Postfix(Expr l) {
            left = l;
        }

        @Override
        public String toString() {
            return "(" + left + "!)";
        }
    }

    public static class Ternary extends Expr {
        public Expr cond;
        public Expr left;
        public Expr right;

        public Ternary(Expr c, Expr l, Expr r) {
            cond = c;
            left = l;
            right = r;
        }

        @Override
        public String toString() {
            return "(" + cond + " ? " + left + " : " + right + ")";
        }
    }

    public abstract String toString();
}
