package no.uio.ifi.asp.runtime;

public class RuntimeStringValue extends RuntimeValue{
    private String stringValue;

    protected RuntimeStringValue(String v){
        this.stringValue = v;
    }

    protected String typeName(){
        return stringValue;
    }

}
