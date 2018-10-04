package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.*;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;


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
}
