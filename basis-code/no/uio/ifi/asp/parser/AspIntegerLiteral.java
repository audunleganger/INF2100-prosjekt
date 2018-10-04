package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.*;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;


class AspIntegerLiteral extends AspAtom{

    AspIntegerLiteral(int n){
        super(n);
    }

    static AspIntegerLiteral parse(Scanner s){
        //does nothing now
        return null;
    }

    @Override
    void prettyPrint(){
        //does nothing now
    }
}
