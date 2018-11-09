package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeStringValue extends RuntimeValue{
    private String stringValue;

    public RuntimeStringValue(String v){
        this.stringValue = v;
    }

    protected String typeName(){
        return "String";
    }

    @Override
    public String toString(){
        return "\'" + stringValue + "\'";
    }

    @Override
    public String getStringValue(String what, AspSyntax where) {
        return stringValue;
    }

    @Override
    public boolean getBoolValue(String what, AspSyntax where) {
        return !stringValue.equals("");
    }

    @Override
    public RuntimeValue evalAdd(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeStringValue) {
            return new RuntimeStringValue(stringValue.concat(v.getStringValue("+ operand", where)));
        }
        runtimeError("Type error +", where);
        return null;
    }

    @Override
    public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeStringValue) {
            return new RuntimeBoolValue(stringValue.equals(v.getStringValue("== operand", where)));
        }
        else if (v instanceof RuntimeNoneValue) {
            return new RuntimeBoolValue(stringValue == null);
        }
        runtimeError("Type error ==", where);
        return null;
    }

    @Override
    public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeStringValue) {
            return new RuntimeBoolValue(!stringValue.equals(v.getStringValue("!= operand", where)));
        }
        else if (v instanceof RuntimeNoneValue) {
            return new RuntimeBoolValue(!(stringValue == null));
        }
        runtimeError("Type error !=", where);
        return null;
    }

    @Override
    public RuntimeValue evalGreater(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeStringValue) {
            for (int a = 0; a < stringValue.length(); a++) {
                if(a == v.getStringValue("operand >", where).length()){
                    return new RuntimeBoolValue(true);
                }
                int c1 = (int) v.getStringValue("operand >", where).charAt(a);
                int c2 = (int) stringValue.charAt(a);

                if(c1 < c2) {
                    return new RuntimeBoolValue(true);
                }
                else if (c2 < c1) {
                    return new RuntimeBoolValue(false);
                }
            }
            return new RuntimeBoolValue(false);
        }
        runtimeError("Type error >", where);
        return null;
    }

    @Override
    public RuntimeValue evalLess(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeStringValue) {
            for (int a = 0; a < stringValue.length(); a++) {
                if(a == v.getStringValue("operand >", where).length()){
                    return new RuntimeBoolValue(false);
                }
                int c1 = (int) v.getStringValue("operand >", where).charAt(a);
                int c2 = (int) stringValue.charAt(a);

                if(c1 < c2) {
                    return new RuntimeBoolValue(false);
                }
                else if (c2 < c1) {
                    return new RuntimeBoolValue(true);
                }
            }
            return new RuntimeBoolValue(true);
        }
        runtimeError("Type error >", where);
        return null;
    }

    @Override
    public RuntimeValue evalLessEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeStringValue) {
            if (stringValue.equals(v.getStringValue("<= operand", where))) {
                return new RuntimeBoolValue(true);
            }
            for (int a = 0; a < stringValue.length(); a++) {
                if(a == v.getStringValue("operand >", where).length()){
                    return new RuntimeBoolValue(false);
                }
                int c1 = (int) v.getStringValue("operand >", where).charAt(a);
                int c2 = (int) stringValue.charAt(a);

                if(c1 < c2) {
                    return new RuntimeBoolValue(false);
                }
                else if (c2 < c1) {
                    return new RuntimeBoolValue(true);
                }
            }
            return new RuntimeBoolValue(true);
        }
        runtimeError("Type error >", where);
        return null;
    }

    @Override
    public RuntimeValue evalGreaterEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeStringValue) {
            if (stringValue.equals(v.getStringValue("<= operand", where))) {
                return new RuntimeBoolValue(true);
            }
            for (int a = 0; a < stringValue.length(); a++) {
                if(a == v.getStringValue("operand >", where).length()){
                    return new RuntimeBoolValue(true);
                }
                int c1 = (int) v.getStringValue("operand >", where).charAt(a);
                int c2 = (int) stringValue.charAt(a);

                if(c1 < c2) {
                    return new RuntimeBoolValue(true);
                }
                else if (c2 < c1) {
                    return new RuntimeBoolValue(false);
                }
            }
            return new RuntimeBoolValue(false);
        }
        runtimeError("Type error >", where);
        return null;
    }

    @Override
    public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where) {
        if(v instanceof RuntimeIntValue) {
            String temp = stringValue;
            for(int a = 0; a < v.getIntValue("operand +", where); a++) {
                temp = temp.concat(stringValue);
            }
            return new RuntimeStringValue(temp);
        }
        runtimeError("Type error +", where);
        return null;
    }

    @Override
    public RuntimeValue evalSubscription(RuntimeValue v, AspSyntax where) {
        if(v instanceof RuntimeIntValue){
            if(v.getIntValue("subscription", where) >= stringValue.length()){
                System.out.println("String Out of bounds error at: " + where.lineNum);
                System.exit(1);
            }
            char temp = stringValue.charAt((int) v.getIntValue("subscription ", where));
            return new RuntimeStringValue("" + temp);
        }
        runtimeError("Type error [..]", where);
        return null;
    }

    @Override
    public RuntimeValue evalNot(AspSyntax where) {
        return new RuntimeBoolValue(stringValue.isEmpty());
    }

    @Override
    public RuntimeValue evalLen(AspSyntax where) {
        return new RuntimeIntValue(stringValue.length());
    }

}
