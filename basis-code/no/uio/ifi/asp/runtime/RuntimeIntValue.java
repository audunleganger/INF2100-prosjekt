package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeIntValue extends RuntimeValue {

    private long intValue;

    public RuntimeIntValue(long v) {
        intValue = v;
    }

    @Override
    protected String typeName() {
        return "Integer";
    }

    @Override
    public String toString() {
        return String.valueOf(intValue);
    }

    @Override
    public long getIntValue(String what, AspSyntax where) {
        return intValue;
    }


    @Override
    public RuntimeValue evalIntDivide(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue) {
            return new RuntimeIntValue(intValue / v.getIntValue("// operand", where));
        }
        else if (v instanceof  RuntimeFloatValue) {
            return new RuntimeFloatValue((long)(intValue / v.getFloatValue("// operand", where)));
        }
        runtimeError("Type error for //.", where);
        return null;
    }

    @Override
    public RuntimeValue evalDivide(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue) {
            return new RuntimeIntValue(intValue / v.getIntValue("/ operand", where));
        }
        else if (v instanceof  RuntimeFloatValue) {
            return new RuntimeFloatValue(intValue / v.getFloatValue("/ operand", where));
        }
        runtimeError("Type error for /.", where);
        return null;
    }

    @Override
    public RuntimeValue evalModulo(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue) {
            return new RuntimeIntValue(intValue % v.getIntValue("% operand", where));
        }
        else if (v instanceof  RuntimeFloatValue) {
            return new RuntimeFloatValue(intValue % v.getFloatValue("% operand", where));
        }
        runtimeError("Type error for %.", where);
        return null;
    }

    @Override
    public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where) {
        if (v instanceof  RuntimeIntValue) {
            return new RuntimeIntValue(intValue * v.getIntValue("* operand", where));
        }
        else if (v instanceof RuntimeFloatValue) {
            return new RuntimeFloatValue(intValue * v.getFloatValue("* operand", where));
        }
        runtimeError("Type error for *.", where);
        return null;
    }

    @Override
    public RuntimeValue evalSubtract(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue) {
            return new RuntimeIntValue(intValue - v.getIntValue("- operand", where));
        }
        else if (v instanceof RuntimeFloatValue) {
            return new RuntimeFloatValue(intValue - v.getFloatValue("- operand", where));
        }
        runtimeError("Type error for -.", where);
        return null;
    }

    @Override
    public RuntimeValue evalAdd(RuntimeValue v, AspSyntax where){
        if (v instanceof RuntimeIntValue) {
            return new RuntimeIntValue(intValue + v.getIntValue("+ operand", where));
        }
        else if (v instanceof RuntimeFloatValue) {
            return new RuntimeFloatValue(intValue + v.getFloatValue("+ operand", where));
        }
        runtimeError("Type error for +.", where);
        return null;
    }

    @Override
    public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue) {
            return new RuntimeBoolValue(intValue == v.getIntValue("== operand", where));
        }
        else if (v instanceof RuntimeFloatValue){
            return new RuntimeBoolValue(intValue == v.getFloatValue("== operand", where));
        }
        runtimeError("Type error for ==.", where);
        return null;
    }

    @Override
    public RuntimeValue evalGreater(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue) {
           return new RuntimeBoolValue(intValue > v.getIntValue("> operand", where));
        }
        else if (v instanceof RuntimeFloatValue) {
            return new RuntimeBoolValue(intValue > v.getIntValue("> operand", where));
        }
        runtimeError("Type error for >.", where);
        return null;
    }

    @Override
    public RuntimeValue evalGreaterEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue) {
            return new RuntimeBoolValue(intValue >= v.getIntValue(">= operand", where));
        }
        else if (v instanceof RuntimeFloatValue) {
            return new RuntimeBoolValue(intValue >= v.getFloatValue(">= operand", where));
        }
        runtimeError("Type error for >=.", where);
        return null;
    }

    @Override
    public RuntimeValue evalLess(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue) {
            return new RuntimeBoolValue(intValue < v.getIntValue("< operand", where));
        }
        else if (v instanceof RuntimeFloatValue) {
            return new RuntimeBoolValue(intValue < v.getFloatValue("< operand", where));
        }
        runtimeError("Type error for <.", where);
        return null;
    }

    @Override
    public RuntimeValue evalLessEqual(RuntimeValue v, AspSyntax where){
        if (v instanceof RuntimeIntValue){
            return new RuntimeBoolValue(intValue <= v.getIntValue("<= operand", where));
        }
        else if (v instanceof  RuntimeFloatValue){
            return new RuntimeBoolValue(intValue <= v.getFloatValue("<= operand", where));
        }
        runtimeError("Type error for =<.", where);
        return null;
    }

    @Override
    public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where){
        if (v instanceof RuntimeIntValue) {
            return new RuntimeBoolValue(intValue != v.getIntValue("!= operand", where));
        }
        else if (v instanceof  RuntimeFloatValue) {
            return new RuntimeBoolValue(intValue != v.getFloatValue("!= operand", where));
        }
        runtimeError("Type error for !=.", where);
        return null;
    }

    @Override
    public RuntimeValue evalNegate(AspSyntax where) {
        if (intValue < 0) {
            return new RuntimeIntValue(intValue);
        }
        runtimeError("Type error for is negative", where);
        return null;
    }

    @Override
    public RuntimeValue evalNot(AspSyntax where) {
        if (intValue == 0) {
            return new RuntimeIntValue(intValue);
        }
        runtimeError("Type error for not", where);
        return null;
    }

    @Override
    public RuntimeValue evalPositive(AspSyntax where) {
        if(intValue > 0) {
            return new RuntimeIntValue(intValue);
        }
        runtimeError("Type error for positive", where);
        return null;
    }

}