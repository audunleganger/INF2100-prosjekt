package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.*;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.runtime.*;



class AspBooleanLiteral extends AspAtom{
    boolean tokenType;

    AspBooleanLiteral(int n){
        super(n);
    }

    // Sjekker hvilken token vi har, og setter tokenType til dette.
    // Returnerer token-objektet med den korrekte tokenTypen.
    static AspBooleanLiteral parse(Scanner s){
        enterParser("Boolean Literal");

        AspBooleanLiteral abl = new AspBooleanLiteral(s.curLineNum());
        if (s.curToken().kind == trueToken) {
            skip(s, trueToken);
            abl.tokenType = true;
        }
        else if (s.curToken().kind == falseToken)   {
            skip(s, falseToken);
            abl.tokenType = false;
        }
        return abl;
    }

    //Se forklaring for prettyPrint() i AspSyntax
    @Override
    void prettyPrint(){
        if (tokenType)  {
            Main.log.prettyWrite("True ");
        }
        else    {
            Main.log.prettyWrite("False " );
        }
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return new RuntimeBoolValue(tokenType);
    }
}
