package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.*;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;


class AspTermOpr extends AspSyntax{

    AspTermOpr(int n){
        super(n);
    }

    static AspTermOpr parse(Scanner n){
        //does nothing yet
        return null
    }

    @Override
    void prettyPrint(){
        //does nothing yet
    }
}
