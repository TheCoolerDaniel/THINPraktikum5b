import java.util.HashMap;

public class Main {

    public static void main(String[] args) {

        String[] expressionsToProcess = {
                "2 3 + 4 6 + +", // Test a: Akzeptierend
                "2 3 + +", // Test b: Verwerfend
                "1 2 + 3 4 + + 5 +", // Test c1: Akzeptierend
                "2 3 + 2 3 + + 2 3 + 2 3 + + +", // Test c2: Akzeptierend
                "2 3 + 2 3 + + 2 3 + 2 3 + + + 2 3 + 2 3 + + 2 3 + 2 3 + + + +", // Test c3: Akzeptierend
                "2 3 4 + +" // Test c4: Akzeptierend
        };

        for(String expression : expressionsToProcess)
        {
            PushdownAutomaton automaton = Main.initializePushdownAutomaton();
            String wordInAutomatonLanguage = convertUPNToAutomatonLanguage(expression);

            if(automaton.processWord(wordInAutomatonLanguage))
            {
                // Der Ausdruck ist in der korrekten Notation und wird ausgerechnet.
                double result = RPNCalculator.calculate(expression);
                System.out.println(expression + "  =  " + (int)result);
            } else {
                // Der Ausdruck hat eine ungültige Notation
                System.out.println("Invalid expression: " + expression);
            }

        }
    }

    public static String convertUPNToAutomatonLanguage(String upn)
    {
        char[] upnChars = upn.toCharArray();
        String translation = "";

        for(char c: upnChars)
        {
            if(Character.getNumericValue(c) > -1 && Character.getNumericValue(c) < 10)
            {
                // char is a number
                translation += "D";
            } else if (c == '+' || c == '-' || c == '*' || c == '/') {
                // char is an operator
                translation += "O";
            } else {
                // char is an illegal character. Skip it.
            }
        }
        return translation;
    }

    /**
     * Creates a Pushdown Automaton as requested in the exercise.
     * @return new PushDown Automaton
     */
    public static PushdownAutomaton initializePushdownAutomaton()
    {
        char[] stack = {'$', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        int anfangsZustand = 0;
        int[] akzeptierteZustaende = {4};
        HashMap<Input, Output> uebergangsfunktionen = initializeTransitions();

        return new PushdownAutomaton(stack, anfangsZustand, akzeptierteZustaende, uebergangsfunktionen);
    }

    /**
     * Creates the transitions for the automaton as requested in the exercise.
     * @return new transitions
     */
    private static HashMap<Input, Output> initializeTransitions()
    {
        HashMap<Input, Output> transitions = new HashMap<Input, Output>();

        Input i00 = new Input(0, 'D', '$');
        Output o00 = new Output(1, "D$");
        transitions.put(i00, o00);

        Input i10 = new Input(1, 'D', 'D');
        Output o10 = new Output(2, "DD");
        transitions.put(i10, o10);

        Input i20 = new Input(2, 'D', 'D');
        Output o20 = new Output(2, "DD");
        transitions.put(i20,o20);
        Input i21 = new Input(2, 'O', 'D');
        Output o21 = new Output(3, "");
        transitions.put(i21, o21);

        Input i30 = new Input(3, 'O', 'D');
        Output o30 = new Output(3, "");
        transitions.put(i30, o30);
        Input i31 = new Input(3, 'E', 'D');
        Output o31 = new Output(4, "");
        transitions.put(i31, o31);

        Input i40 = new Input(4, 'D', '$');
        Output o40 = new Output(5, "D$");
        transitions.put(i40, o40);

        Input i50 = new Input(5, 'D', 'D');
        Output o50 = new Output(5, "DD");
        transitions.put(i50, o50);
        Input i51 = new Input(5, 'O', 'D');
        Output o51 = new Output(3, "DD");
        transitions.put(i51, o51);

        return transitions;
    }
}
