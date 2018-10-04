package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.*;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.runtime.*;



class AspBooleanLiteral extends AspAtom{

    AspBooleanLiteral(int n){
        super(n);
    }

    static AspBooleanLiteral parse(Scanner s){
        // does nothing now
        return null;
    }

    @Override
    void prettyPrint(){
        //does nothing now
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 3:
        return null;
    }
}
