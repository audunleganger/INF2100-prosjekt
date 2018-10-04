package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.*;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;


class AspSuite extends AspSyntax{

    AspSuite(int n){
        super(n);
    }

    static AspSuite parse(Scanner s){
        //does nothing
        return null;
    }

    @Override
    void prettyPrint(){
        //does nothing
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 3:
        return null;
    }
}
