package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.parser.AspSyntax;

import java.util.ArrayList;

public class RuntimeDictionaryValue extends RuntimeValue{

    private ArrayList<RuntimeValue> dictionaryValues;

    public RuntimeDictionaryValue (ArrayList<RuntimeValue> dictionaryValues){
        this.dictionaryValues = dictionaryValues;
    }

    @Override
    public int size(String what, AspSyntax where){
        return dictionaryValues.size()/2;
    }

    @Override
    public ArrayList<RuntimeValue> getListValues(String what, AspSyntax where){
        return dictionaryValues;
    }

    public String typeName(){
        return "Dictionary";
    }

    public RuntimeValue getKey(int teller, AspSyntax where) {
        if (dictionaryValues.isEmpty()){
            return new RuntimeNoneValue();
        }
        int temp = 0;

        for (int i = 0; i <dictionaryValues.size()/2; i++) {
            if(i == teller) {
                return dictionaryValues.get(temp);
            }
            temp += 2;
        }
        runtimeError("Out of bounds dictionary exception", where);
        return null;
    }

    @Override
    public String toString() {
        if(dictionaryValues.isEmpty()) {
            return "{}";
        }
        String temp = "{";
        int teller = 0;
        for(int i = 0; i<dictionaryValues.size()/2; i++) {
            if (teller == dictionaryValues.size() - 2) {
                temp = temp.concat(dictionaryValues.get(teller).toString() + ":");
                teller += 1;
                temp = temp.concat(dictionaryValues.get(teller).toString() + "}");
                break;
            }
            temp = temp.concat(dictionaryValues.get(teller).toString() + ":");
            teller += 1;
            temp = temp.concat(dictionaryValues.get(teller).toString() + ",");
            teller +=1;
        }
        return temp;
    }

    @Override
    public boolean getBoolValue(String what, AspSyntax where) {
        return !dictionaryValues.isEmpty();
    }

    @Override
    public RuntimeValue evalNot(AspSyntax where) {
        return new RuntimeBoolValue(dictionaryValues.isEmpty());
    }

    @Override
    public RuntimeValue evalLen(AspSyntax where) {
        return new RuntimeIntValue(dictionaryValues.size()/2);
    }

    @Override
    public RuntimeValue evalSubscription(RuntimeValue v, AspSyntax where) {
        int teller = 0;
        for(int a = 0; a<dictionaryValues.size()/2; a++){
            if (a == dictionaryValues.size()/2) {
                return new RuntimeNoneValue();
            }
            if (dictionaryValues.get(teller).getStringValue("Subscription error", where).equals(
                    v.getStringValue("Subscription error", where))){
                return dictionaryValues.get(teller + 1);
            }
            teller += 2;
        }
        runtimeError("Type error evalSubscription ", where);
        return null;
    }

    @Override
    public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {
        if(v instanceof RuntimeNoneValue) {
            return new RuntimeBoolValue(false);
        }
        runtimeError("Type error ==", where);
        return null;
    }

    @Override
    public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where) {
        if(v instanceof RuntimeNoneValue) {
            return new RuntimeBoolValue(true);
        }
        runtimeError("Type error !=", where);
        return null;
    }

    @Override
    public void evalAssignElem(RuntimeValue inx, RuntimeValue val, AspSyntax where) {
        int teller = 0;

        for(int i = 0; i<dictionaryValues.size()/2; i++) {
            if(dictionaryValues.get(teller).toString().equals(inx.toString())) {
                dictionaryValues.set(teller + 1, val);
                return;
            }
            teller += 2;
        }

        runtimeError("key doesn't exist ", where);
    }
}
