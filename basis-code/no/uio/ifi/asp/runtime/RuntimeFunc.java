package no.uio.ifi.asp.runtime;
import no.uio.ifi.asp.parser.AspSyntax;
import java.util.ArrayList;


public class RuntimeFunc extends RuntimeValue{
    public String word;
    AspSyntax body;
    ArrayList<String> paramNames;
    RuntimeScope outer;

    public RuntimeFunc(String word){
        this.word = word;
    }

    public void assignScope(RuntimeScope outer) {
        this.outer = outer;
    }

    public String getWord() {
        return word;
    }

    public RuntimeFunc(AspSyntax body, ArrayList<String> paramNames, RuntimeScope outer) {
        this.body = body;
        this.paramNames = paramNames;
        this.outer = outer;
    }

    public String typeName(){
        return "Function call";
    }

    @Override
    public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams, AspSyntax where){
        RuntimeScope l = new RuntimeScope(outer);

        if(paramNames.size() != actualParams.size()) {
            runtimeError("To little/to Many arguments, need: "
                    + paramNames.size() + "got: " + actualParams.size(), where);
        }

        int teller = 0;
        for (String g : paramNames) {
            l.assign(g, actualParams.get(teller));
            teller++;
        }

        try{
            body.eval(l);
        }catch (RuntimeReturnValue rrv) {
            return rrv.value;
        }
        return new RuntimeNoneValue();

    }
}
