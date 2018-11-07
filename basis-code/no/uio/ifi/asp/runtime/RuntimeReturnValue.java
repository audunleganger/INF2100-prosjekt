package no.uio.ifi.asp.runtime;

// For part 4:

public class RuntimeReturnValue extends Exception {
    private int lineNum;
    private RuntimeValue value;

    public RuntimeReturnValue(RuntimeValue v, int lNum) {
        value = v;  lineNum = lNum;
    }
}
