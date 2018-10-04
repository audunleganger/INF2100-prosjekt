package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.*;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.runtime.*;



class AspFloatLiteral extends AspAtom{
    String word;

    AspFloatLiteral(int n){
        super(n);
    }

    static AspFloatLiteral parse(Scanner s){
        enterParser("Float Literal");

        AspFloatLiteral afl = new AspFloatLiteral(s.curLineNum());
        af1.word = s.curToken().kind.toString();
        skip(s, floatToken);

        leaveParser("Float Literal");

        return afl;
    }

    @Override
    void prettyPrint(){
        word = " " + word + " ";
        Main.log.prettyWrite(word);
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 3:
        return null;
    }
}
