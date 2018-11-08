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

    // Lagrer verdien som har blitt lest i variabelen word,
    // og skipper floatToken
    static AspFloatLiteral parse(Scanner s){
        enterParser("Float Literal");

        AspFloatLiteral afl = new AspFloatLiteral(s.curLineNum());
        afl.word = Double.toString(s.curToken().floatLit);
        skip(s, floatToken);

        leaveParser("Float Literal");

        return afl;
    }

    //Se forklaring for prettyPrint() i AspSyntax
    @Override
    void prettyPrint(){
        word = word + " ";
        Main.log.prettyWrite(word);
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return new RuntimeFloatValue(Double.parseDouble(s));
    }
}
