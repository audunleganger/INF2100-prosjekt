package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.*;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.runtime.*;



class AspFactorPrefix extends AspSyntax{
    static String sign;

    AspFactorPrefix(int n) {
        super(n);
    }

    // Sjekker om vi jobber med en + eller - operator, og setter variabelen
    // sign til denne, og skipper den respektive tokenen
    static AspFactorPrefix parse(Scanner s){
        AspFactorPrefix afp = new AspFactorPrefix(s.curLineNum());
        enterParser("Factor Prefix");
        if (s.curToken().kind == plusToken) {
            sign = "+";
            skip(s, plusToken);
        }
        else if (s.curToken().kind == minusToken)   {
            sign = "-";
            skip(s, minusToken);
        }
        leaveParser("Factor Prefix");
        return afp;
    }

    //Se forklaring for prettyPrint() i AspSyntax
    @Override
    void prettyPrint() {
        Main.log.prettyWrite(sign);
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 3:
        return null;
    }
}
