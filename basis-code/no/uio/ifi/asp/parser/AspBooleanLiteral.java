package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.*;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;


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
}
