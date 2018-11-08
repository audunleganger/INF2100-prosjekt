package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.parser.AspSyntax;

import java.util.HashMap;

public class RuntimeDictionaryValue extends RuntimeValue{

    private HashMap<RuntimeValue, RuntimeValue> dictionaryValue;

    protected RuntimeDictionaryValue (HashMap<RuntimeValue, RuntimeValue> v){
        dictionaryValue = v;
    }

    public HashMap<RuntimeValue,RuntimeValue> getDictionaryValue(){
        return dictionaryValue;
    }

    public String typeName(){
        return "Dictionary";
    }

    @Override
    public RuntimeValue evalNot(AspSyntax where) {
        return new RuntimeBoolValue(dictionaryValue.isEmpty());
    }

    @Override
    public RuntimeValue evalLen(AspSyntax where) {
        return new RuntimeIntValue(dictionaryValue.size());
    }

    @Override
    public RuntimeValue evalSubscription(RuntimeValue v, AspSyntax where) {
        return dictionaryValue.get(v);
    }
}
