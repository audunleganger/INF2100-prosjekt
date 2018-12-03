package no.uio.ifi.asp.runtime;
import no.uio.ifi.asp.parser.AspSyntax;
import java.util.ArrayList;


public class RuntimeFunc extends RuntimeValue{
    String word;
    AspSyntax body;
    ArrayList<String> paramNames;

    public RuntimeFunc(String word){
        this.word = word;
    }

    public RuntimeFunc(AspSyntax body, ArrayList<String> paramNames) {
        this.body = body;
        this.paramNames = paramNames;
    }

    public String typeName(){
        return "Function call";
    }

    @Override
    public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams, AspSyntax where){
        RuntimeScope l = new RuntimeLibrary();
        RuntimeScope newScope = new RuntimeScope(l);

        if(paramNames.size() != actualParams.size()) {
            runtimeError("To little/to Many arguments, need: "
                    + paramNames.size() + "got: " + actualParams.size(), where);
        }

        int teller = 0;
        for (String g : paramNames) {
            newScope.assign(g, actualParams.get(teller));
            teller++;
        }

        try{
            body.eval(newScope);
        }catch (RuntimeReturnValue rrv) {
            return rrv.value;
        }
        return new RuntimeNoneValue();

    }
}
