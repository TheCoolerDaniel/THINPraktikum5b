public class Input {
    private int currentStateNumber;
    private char inputChar;
    private char poppedChar;

    public Input(int currentStateNumber, char inputChar, char poppedChar) {
        this.currentStateNumber = currentStateNumber;
        this.inputChar = inputChar;
        this.poppedChar = poppedChar;
    }

    public int getCurrentStateNumber() {
        return currentStateNumber;
    }

    public char getInputChar() {
        return inputChar;
    }

    public char getPoppedChar() {
        return poppedChar;
    }

    @Override
    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (!(o instanceof Input)) {
            return false;
        }

        Input input = (Input) o;

        return input.getCurrentStateNumber() == currentStateNumber &&
                input.getInputChar() == inputChar &&
                input.getPoppedChar() == getPoppedChar();
    }

    @Override
    public int hashCode()
    {
        int result = 17;
        result = 31 * result + currentStateNumber;
        result = 31 * result + inputChar;
        result = 31 * result + poppedChar;
        return  result;
    }
}