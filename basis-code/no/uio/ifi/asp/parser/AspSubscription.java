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

    // Skipper den omsluttende [], og parser innholdet i det indre uttrykket via
    // AspExpr sin parse-metode
    static AspSubscription parse(Scanner s){
        enterParser("Subscription");

        AspSubscription as = new AspSubscription(s.curLineNum());

        skip(s,leftBracketToken);
        as.expr = AspExpr.parse(s);
        skip(s, rightBracketToken);

        leaveParser("Subscription");

        return as;
    }

    //Se forklaring for prettyPrint() i AspSyntax
    @Override
    void prettyPrint(){
        Main.log.prettyWrite("[ ");
        expr.prettyPrint();
        Main.log.prettyWrite("] ");
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        RuntimeValue v = expr.get(0).eval(curScope);
        for (int i = 1; i < expr.size(); i++)   {
            v = expr.get(i).eval(curScope);
        }
        return v;
    }

}
