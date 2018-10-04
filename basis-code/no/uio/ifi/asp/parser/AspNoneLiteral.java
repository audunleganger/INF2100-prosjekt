package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.*;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.runtime.*;



class AspNoneLiteral extends AspAtom{

    AspNoneLiteral(int n){
        super(n);
    }

    static AspNoneLiteral parse(Scanner s){
        enterParser("None Literal");
        AspNoneLiteral anl = new AspNoneLiteral(s.curLineNum());
        skip(s, noneToken);
        leaveParser("None Literal");
        return anl;
    }

    @Override
    void prettyPrint(){
        // Does nothing
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 3:
        return null;
    }
}
