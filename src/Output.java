public class Output {
    private int nextStateNumber;
    private String pushedString;

    public Output(int nextStateNumber, String pushedString) {
        this.nextStateNumber = nextStateNumber;
        this.pushedString = pushedString;
    }

    public int getNextStateNumber() {
        return nextStateNumber;
    }

    public String getPushedString() {
        return pushedString;
    }
}