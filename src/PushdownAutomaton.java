import java.util.Map;

public class PushdownAutomaton
{
    char[] stack;
    int currentState; // Aktueller Zustand
    int[] acceptedStates; // Akzeptierte Zustände
    Map<Input, Output> transitions; // Uebergangsfunktionen

    public PushdownAutomaton(char[] stack, int initialState, int[] acceptedStates, Map<Input, Output> transitions)
    {
        this.stack = stack;
        this.currentState = initialState;
        this.acceptedStates = acceptedStates;
        this.transitions = transitions;
    }

    public boolean processWord(String inputWord)
    {
        char[] inputChars = inputWord.toCharArray();

        for(char c : inputChars)
        {
            Output pathWithoutEpsilon = null;
            Output pathWithEpsilon = null;

            for(Map.Entry<Input, Output> entry : transitions.entrySet())
            {

                Input i = entry.getKey();
                Output o = entry.getValue();

                if(i.getCurrentStateNumber() == currentState)
                {
                    if(i.getInputChar() == c)
                    {
                        // Prefer the path that doesn't go with the epsilon
                        if(i.getPoppedChar() == stack[currentStackIndex()-1])
                        {
                            pathWithoutEpsilon = o;
                        }
                    }

                    if(i.getInputChar() == 'E') {
                        if(i.getPoppedChar() == stack[currentStackIndex()-1])
                        {
                            pathWithEpsilon = o;
                        }
                    }
                }

            }

            if(pathWithoutEpsilon != null)
            {
                char poppedChar = pop();
                currentState = pathWithoutEpsilon.getNextStateNumber();
                String stringToPush = pathWithoutEpsilon.getPushedString();
                push(stringToPush);
            }
            else if(pathWithEpsilon != null)
            {
                char poppedChar = pop();
                currentState = pathWithEpsilon.getNextStateNumber();
                String stringToPush = pathWithEpsilon.getPushedString();
                push(stringToPush);
            }
        }

        for(Map.Entry<Input, Output> entry : transitions.entrySet())
        {
            Input i = entry.getKey();
            Output o = entry.getValue();

            if(i.getCurrentStateNumber() == currentState)
            {
                if(i.getInputChar() == 'E') {

                    if(i.getPoppedChar() == stack[currentStackIndex()-1])
                    {
                        char poppedChar = pop();
                        currentState = o.getNextStateNumber();
                        String stringToPush = o.getPushedString();
                        push(stringToPush);
                    }
                }
            }

        }

        for(int acceptedState : acceptedStates)
        {
            if(currentState == acceptedState)
            {
                return true;
            }
        }
        return false;
    }

    public void printStack()
    {
        System.out.print("Stack: {");
        for(char c : stack)
        {
            System.out.print("'" + c + "' ");
        }
        System.out.println();
    }

    private void push(String wordToPush)
    {
        char[] charsToPush = wordToPush.toCharArray();
        push(charsToPush);
    }

    private void push(char[] charsToPush)
    {
        int amountOfCharactersToAdd = charsToPush.length;
        while(amountOfCharactersToAdd > 0)
        {
            if(currentStackIndex()+1 >= stack.length -1)
            {
                System.err.println("The stack is full");
            } else {
                stack[currentStackIndex()] = charsToPush[amountOfCharactersToAdd-1];
                amountOfCharactersToAdd--;
            }
        }
    }

    char pop()
    {
        if(currentStackIndex() <= 0)
        {
            System.err.println("Popped empty stack.");
            return 0;
        } else {
            int index = currentStackIndex()-1;
            char poppedChar = stack[index];
            stack[index] = 0;
            return poppedChar;
        }
    }

    /**
     * This method returns the index of the last character in the stack.
     * @return index
     */
    private int currentStackIndex()
    {
        int index = 0;
        boolean lastCharacterNotFound = true;
        while(index < stack.length-1)
        {
            if(stack[index] == 0)
            {
                return index;
            } else {
                index++;
            }
        }

        // The stack is full. Return the max index.
        return index;
    }
}
