package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.*;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.runtime.*;



class AspBooleanLiteral extends AspAtom{
    static boolean tokenType;
    AspBooleanLiteral(int n){
        super(n);
    }

    static AspBooleanLiteral parse(Scanner s){
        enterParser("Boolean Literal");

        AspBooleanLiteral abl = new AspBooleanLiteral(s.curLineNum());
        if (s.curToken().kind == trueToken) {
            skip(s, trueToken);
            tokenType = true;
        }
        else if (s.curTOken().kind == falseToken)   {
            skip(s, falseToken);
            tokenType = false;
        }
        return null;
    }

    @Override
    void prettyPrint(){
        if (tokenType)  {
            Main.log.prettyWrite(" True ");
        }
        else    {
            Main.log.prettyWrite(" False" );
        }
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 3:
        return null;
    }
}
