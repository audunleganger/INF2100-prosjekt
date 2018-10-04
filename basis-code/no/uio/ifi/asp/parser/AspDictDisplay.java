package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.*;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;


class AspDictDisplay extends AspAtom{

    AspDictDisplay(int n){
        super(n);
    }

    static AspDictDisplay parse(Scanner s){
        //does nothing now
        return null;
    }

    @Override
    void prettyPrint(){
        //Does nothing now
    }
}
