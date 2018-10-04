package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.*;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.runtime.*;



class AspStringLiteral extends AspAtom{
    String word;

    AspStringLiteral(int n){
        super(n);
    }

    static AspStringLiteral parse(Scanner s){
        enterParser("String Literal");

        AspStringLiteral asl = new AspStringLiteral(s.curLineNum());
        asl.word = s.curToken().stringLit;
        skip(s, stringToken);

        leaveParser("String Literal");

        return asl;
    }

    @Override
    void prettyPrint() {
        word = "\"" + word + "\"";
        Main.log.prettyWrite(word);
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 3:
        return null;
    }
}
