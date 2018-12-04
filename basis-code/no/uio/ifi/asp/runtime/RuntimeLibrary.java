package no.uio.ifi.asp.runtime;

import java.util.ArrayList;
import java.util.Scanner;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeLibrary extends RuntimeScope {
    private Scanner keyboard = new Scanner(System.in);

    public RuntimeLibrary() {
        // len
        assign("len", new RuntimeFunc("len") {
            @Override
            public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams, AspSyntax where) {
                checkNumParams(actualParams, 1, "len", where);
                return actualParams.get(0).evalLen(where);
            }});
        // print
        assign("print", new RuntimeFunc("print") {
            @Override
            public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams, AspSyntax where) {
                for (int i = 0; i < actualParams.size(); ++i) {
                    if (i > 0) System.out.print(" ");
                    System.out.print(actualParams.get(i).toString());
                }
                System.out.println();
                return new RuntimeNoneValue();
            }});

        //str
        assign("str", new RuntimeFunc("str") {
            @Override
            public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams, AspSyntax where) {
                return new RuntimeStringValue(actualParams.get(0).toString());
            }
        });

        //float
        assign("float", new RuntimeFunc("float") {
            @Override
            public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams, AspSyntax where) {
                if (actualParams.get(0) instanceof RuntimeIntValue) {
                    return new RuntimeFloatValue(actualParams.get(0).getIntValue("Convert", where));
                }
                else if (actualParams.get(0) instanceof  RuntimeFloatValue) {
                    return actualParams.get(0);
                }
                else if (actualParams.get(0) instanceof  RuntimeStringValue) {
                    double val = Double.parseDouble(actualParams.get(0).toString());
                    return new RuntimeFloatValue(val);
                }
                else {
                    runtimeError("Expected int/float/string got: " +
                            actualParams.get(0).typeName(), where);
                    return null;
                }
            }
        });

        //int
        assign("int", new RuntimeFunc("int") {
            @Override
            public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams, AspSyntax where) {
                if (actualParams.get(0) instanceof RuntimeIntValue) {
                    return actualParams.get(0);
                }
                else if (actualParams.get(0) instanceof  RuntimeFloatValue) {
                    return new RuntimeIntValue((long) actualParams.get(0).getFloatValue("Convert", where));
                }
                else if (actualParams.get(0) instanceof  RuntimeStringValue) {
                    long val = Integer.parseInt(actualParams.get(0).toString());
                    return new RuntimeIntValue(val);
                }
                else {
                    runtimeError("Expected int/float/string got: " +
                            actualParams.get(0).typeName(), where);
                    return null;
                }
            }
        });

        //range
        assign("range", new RuntimeFunc("range") {
            @Override
            public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams, AspSyntax where) {
                if(actualParams.size() != 2) {
                    runtimeError("To little or to many arg in ", where);
                }

                else if (!(actualParams.get(0) instanceof RuntimeIntValue) ||
                        !(actualParams.get(1) instanceof RuntimeIntValue)) {
                    runtimeError("Expected a int got: " + actualParams.get(0).typeName()
                    + " in line: ", where);
                }

                ArrayList<RuntimeValue> number_list = new ArrayList<>();
                int number1 = (int) actualParams.get(0).getIntValue("Range func", where);
                int number2 = (int) actualParams.get(0).getIntValue("Range func", where);

                for(int i = number1; i<number2; i++) {
                    number_list.add(new RuntimeIntValue(i));
                }

                return new RuntimeListValue(number_list);
            }
        });

    }


    private void checkNumParams(ArrayList<RuntimeValue> actArgs, int nCorrect, String id, AspSyntax where) {
	    if (actArgs.size() != nCorrect)
	        RuntimeValue.runtimeError("Wrong number of parameters to " + id + "!",where);
        }
}
