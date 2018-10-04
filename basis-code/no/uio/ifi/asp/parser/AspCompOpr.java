package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.*;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.runtime.*;



class AspCompOpr extends AspSyntax{

    AspCompOpr(int n){
        super(n);
    }

    static AspCompOpr parse(Scanner s){
        // does nothing right now
        return null;
    }

    @Override
    void prettyPrint() {
        // does nothing right now
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 3:
        return null;
    }
}
