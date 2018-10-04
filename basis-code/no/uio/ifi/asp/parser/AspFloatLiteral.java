package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.*;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;


class AspFloatLiteral extends AspAtom{

    AspFloatLiteral(int n){
        super(n)
    }

    static AspFloatLiteral parse(Scanner s){
        //do nothing now
        return null
    }

    @Override
    void prettyPrint(){
        //does nothing now
    }
}
