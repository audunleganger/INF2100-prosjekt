package no.uio.ifi.asp.runtime;
import java.util.ArrayList;
import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeListValue extends RuntimeValue{

    private ArrayList<RuntimeValue> listValues;

    public RuntimeListValue (ArrayList<RuntimeValue> v) {
        listValues = v;
    }

    public String typeName() {
        return "List";
    }

    public ArrayList<RuntimeValue> getListValues(){
        return listValues;
    }

    @Override
    public String toString() {
        String temp = "[";
        for(int i = 0; i<listValues.size(); i++){
            if(i == listValues.size() - 1) {
                temp = temp.concat(listValues.get(i).toString() + "]");
                break;
            }
            temp = temp.concat(listValues.get(i).toString() + ", ");
        }
        return temp;
    }

    @Override
    public boolean getBoolValue(String what, AspSyntax where) {
        return !listValues.isEmpty();
    }

    @Override
    public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue) {
            ArrayList<RuntimeValue> temp = listValues;
            for(int a = 0; a < v.getIntValue("operand *", where); a++) {
                temp.addAll(listValues);
            }
            return new RuntimeListValue (temp);
        }
        runtimeError("Error for operand *", where);
        return null;
    }

    @Override
    public RuntimeValue evalNot(AspSyntax where) {
        return new RuntimeBoolValue(listValues.isEmpty());
    }

    @Override
    public RuntimeValue evalLen(AspSyntax where) {
        return new RuntimeIntValue(listValues.size());
    }

    @Override
    public RuntimeValue evalSubscription(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue) {
            return listValues.get((int) v.getIntValue("get operand", where));
        }
        runtimeError("Type error get", where);
        return null;
    }
}
