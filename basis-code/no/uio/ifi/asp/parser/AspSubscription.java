package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.*;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.runtime.*;



class AspSubscription extends AspPrimarySuffix{
    AspExpr expr;

    AspSubscription(int n){
        super(n);
    }

    static AspSubscription parse(Scanner s){
        enterParser("Subscription");

        AspSubscription as = new AspSubscription(s.curLineNum());

        skip(s,leftBracketToken);
        as.expr = AspExpr.parse(s);
        skip(s, rightBracketToken);

        leaveParser("Subscription");

        return as;
    }

    @Override
    void prettyPrint(){
        Main.log.prettyWrite("[");
        expr.prettyPrint();
        Main.log.prettyWrite("]");
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 3:
        return null;
    }

}
