package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeFloatValue extends RuntimeValue {

    private double floatValue;

    protected RuntimeFloatValue(double v) {
        floatValue = v;
    }

    @Override
    public String typeName() {
        return "Float";
    }

    @Override
    public String toString() {
        return String.valueOf(floatValue);
    }

    @Override
    public double getFloatValue(String what, AspSyntax where){
        return floatValue;
    }

    @Override
    public RuntimeValue evalIntDivide(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue) {
            return new RuntimeIntValue((long)(floatValue / v.getIntValue("// operand", where)));
        }
        else if (v instanceof  RuntimeFloatValue) {
            return new RuntimeFloatValue((long)(floatValue / v.getFloatValue("// operand", where)));
        }
        runtimeError("Type error for //.", where);
        return null;
    }

    @Override
    public RuntimeValue evalDivide(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue) {
            return new RuntimeIntValue((long)(floatValue / v.getIntValue("/ operand", where)));
        }
        else if (v instanceof  RuntimeFloatValue) {
            return new RuntimeFloatValue(floatValue / v.getFloatValue("/ operand", where));
        }
        runtimeError("Type error for /.", where);
        return null;
    }

    @Override
    public RuntimeValue evalModulo(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue) {
            return new RuntimeIntValue((long)(floatValue % v.getIntValue("% operand", where)));
        }
        else if (v instanceof  RuntimeFloatValue) {
            return new RuntimeFloatValue(floatValue % v.getFloatValue("% operand", where));
        }
        runtimeError("Type error for %.", where);
        return null;
    }

    @Override
    public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where) {
        if (v instanceof  RuntimeIntValue) {
            return new RuntimeIntValue((long)(floatValue * v.getIntValue("* operand", where)));
        }
        else if (v instanceof RuntimeFloatValue) {
            return new RuntimeFloatValue(floatValue* v.getFloatValue("* operand", where));
        }
        runtimeError("Type error for *.", where);
        return null;
    }

    @Override
    public RuntimeValue evalSubtract(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue) {
            return new RuntimeIntValue((long)(floatValue - v.getIntValue("- operand", where)));
        }
        else if (v instanceof RuntimeFloatValue) {
            return new RuntimeFloatValue(floatValue - v.getFloatValue("- operand", where));
        }
        runtimeError("Type error for -.", where);
        return null;
    }

    @Override
    public RuntimeValue evalAdd(RuntimeValue v, AspSyntax where){
        if (v instanceof RuntimeIntValue) {
            return new RuntimeIntValue((long) (floatValue + v.getIntValue("+ operand", where)));
        }
        else if (v instanceof RuntimeFloatValue) {
            return new RuntimeFloatValue((long) (floatValue + v.getFloatValue("+ operand", where)));
        }
        runtimeError("Type error for +.", where);
        return null;
    }

    @Override
    public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue) {
            return new RuntimeBoolValue((floatValue == v.getIntValue("== operand", where)));
        }
        else if (v instanceof RuntimeFloatValue){
            return new RuntimeBoolValue(floatValue == v.getFloatValue("== operand", where));
        }
        runtimeError("Type error for ==.", where);
        return null;
    }

    @Override
    public RuntimeValue evalGreater(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue) {
            return new RuntimeBoolValue(floatValue > v.getIntValue("> operand", where));
        }
        else if (v instanceof RuntimeFloatValue) {
            return new RuntimeBoolValue(floatValue > v.getIntValue("> operand", where));
        }
        runtimeError("Type error for >.", where);
        return null;
    }

    @Override
    public RuntimeValue evalGreaterEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue) {
            return new RuntimeBoolValue(floatValue >= v.getIntValue(">= operand", where));
        }
        else if (v instanceof RuntimeFloatValue) {
            return new RuntimeBoolValue(floatValue >= v.getFloatValue(">= operand", where));
        }
        runtimeError("Type error for >=.", where);
        return null;
    }

    @Override
    public RuntimeValue evalLess(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue) {
            return new RuntimeBoolValue(floatValue < v.getIntValue("< operand", where));
        }
        else if (v instanceof RuntimeFloatValue) {
            return new RuntimeBoolValue(floatValue < v.getFloatValue("< operand", where));
        }
        runtimeError("Type error for <.", where);
        return null;
    }

    @Override
    public RuntimeValue evalLessEqual(RuntimeValue v, AspSyntax where){
        if (v instanceof RuntimeIntValue){
            return new RuntimeBoolValue(floatValue <= v.getIntValue("<= operand", where));
        }
        else if (v instanceof  RuntimeFloatValue){
            return new RuntimeBoolValue(floatValue <= v.getFloatValue("<= operand", where));
        }
        runtimeError("Type error for =<.", where);
        return null;
    }

    @Override
    public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where){
        if (v instanceof RuntimeIntValue) {
            return new RuntimeBoolValue(floatValue != v.getIntValue("!= operand", where));
        }
        else if (v instanceof  RuntimeFloatValue) {
            return new RuntimeBoolValue(floatValue != v.getFloatValue("!= operand", where));
        }
        runtimeError("Type error for !=.", where);
        return null;
    }

    @Override
    public RuntimeValue evalNegate(AspSyntax where) {
        if (floatValue < 0) {
            return new RuntimeFloatValue(floatValue);
        }
        runtimeError("Type error for is negative", where);
        return null;
    }

    @Override
    public RuntimeValue evalNot(AspSyntax where) {
        if (floatValue == 0) {
            return new RuntimeFloatValue(floatValue);
        }
        runtimeError("Type error for not", where);
        return null;
    }

    @Override
    public RuntimeValue evalPositive(AspSyntax where) {
        if(floatValue > 0) {
            return new RuntimeFloatValue(floatValue);
        }
        runtimeError("Type error for positive", where);
        return null;
    }

}
