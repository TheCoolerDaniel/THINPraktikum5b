public class RPNCalculator {

    /**
     * Calculates the result of an expression in Reverse Polish Notation
     *
     * @param expr the expression to calculate
     */
    public static double calculate(String expr) {

        double[] stack = new double[100];

        for (String token : expr.split("\\s+")) {
            switch (token) {
                case "+":
                    push(stack,pop(stack) + pop(stack));
                    break;
                case "-":
                    push(stack,-pop(stack) + pop(stack));
                    break;
                case "*":
                    push(stack,pop(stack) * pop(stack));
                    break;
                case "/":
                    double divisor = pop(stack);
                    push(stack, pop(stack) / divisor);
                    break;
                default:
                    push(stack, Double.parseDouble(token));
                    break;
            }
        }
        return pop(stack);
    }

    private static double pop(double[] stack)
    {
        int lastStackIndex = lastStackIndex(stack);
        double lastStackEntry = 0;

        if(lastStackIndex >= 0)
        {
            lastStackEntry = stack[lastStackIndex];
            stack[lastStackIndex] = 0;
            return lastStackEntry;
        } else {
            // The stack is empty.
            return 0;
        }
    }

    private static int lastStackIndex(double[] stack)
    {
        for(int i = 0; i < stack.length; i++)
        {
            if(stack[i] == 0)
            {
                return i-1;
            }
        }

        // The stack is full.
        return stack.length-1;
    }

    private static void push(double[] stack, double toPush)
    {
        if(lastStackIndex(stack)+1 >= stack.length -1)
        {
            System.err.println("The stack is full.");
        } else {
            stack[lastStackIndex(stack)+1] = toPush;
        }
    }
}