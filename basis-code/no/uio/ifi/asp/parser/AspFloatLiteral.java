package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.*;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;


class AspFloatLiteral extends AspAtom{

    AspFloatLiteral(int n){
        super(n);
    }

    static AspFloatLiteral parse(Scanner s){
        enterParser("Float Literal");

        AspFloatLiteral afl = new AspFLoatLiteral(s.curLineNum());

        skip(s, stringToken);

        leaveParser("Float Literal");

        return afl;
    }

    @Override
    void prettyPrint(){
        String word = " " + s.curToken().kind.toString() + " ";
        Main.log.prettyWrite(word);
    }
}
