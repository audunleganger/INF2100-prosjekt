package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.*;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;


class AspIntegerLiteral extends AspAtom{
    String word;

    AspIntegerLiteral(int n){
        super(n);
    }

    static AspIntegerLiteral parse(Scanner s){
        enterParser("Integer Literal");

        AspIntegerLiteral ail = new AspIntegerLiteral(s.curLineNum());
        ail.word = s.curToken().kind.toString();

        skip(s, integerToken);

        leaveParser("Integer Token");

        return ail;
    }

    @Override
    void prettyPrint(){
        String word = " " + word + " ";
        Main.log.prettyWrite(word);
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 3:
        return null;
    }
}
