package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.*;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;


class AspStringLiteral extends AspAtom{

    AspStringLiteral(int n){
        super(n);
    }

    static AspStringLiteral parse(Scanner s){
        enterParser("String Literal");

        AspStringLiteral asl = new AspStringLiteral(s.curLineNum());

        skip(s, stringToken);

        leaveParser("String Literal");

        return asl;
    }

    @Override
    void prettyPrint() {
        String word = "\" " + s.curToken().kind.toString() + " \"";
        Main.log.prettyWrite(word);
    }
}
