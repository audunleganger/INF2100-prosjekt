package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.*;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;


class AspIntegerLiteral extends AspAtom{

    AspIntegerLiteral(int n){
        super(n);
    }

    static AspIntegerLiteral parse(Scanner s){
        enterParser("Integer Literal");

        AspIntegerLiteral ail = new AspIntegerLiteral(s.curLineNum());

        skip(s, integerToken);

        leaveParser("Integer Token");

        return ail;
    }

    @Override
    void prettyPrint(){
        String word = " " + s.curToken().kind.toString() + " ";
        Main.log.prettyWrite(word);
    }
}
