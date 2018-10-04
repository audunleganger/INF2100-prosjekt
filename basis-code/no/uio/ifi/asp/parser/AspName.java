package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.*;
import no.uio.ifi.asp.main.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;


class AspName extends AspAtom{

    AspName(int n){
        super(n)
    }

    static AspName parse(Scanner s) {
        enterParser("Name");

        AspName an = new AspName(s.curLineNum());
        skip(s,nameToken);
        
        leaveParser("Name");

        return an;
    }

    @Override
    void prettyPrint() {
        String word = " " + s.curToken().kind.toString() + " ";
        Main.log.prettyWrite(word);
    }
}
