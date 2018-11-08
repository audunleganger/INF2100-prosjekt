package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.*;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.runtime.*;



class AspInnerExpr extends AspAtom{
    AspExpr exp;

    AspInnerExpr(int n){
        super(n);
    }

    // Skipper de omsluttende parentesen, og parser uttrykket mellom dem
    // via AspExpr sin parse-metode
    static AspInnerExpr parse(Scanner s){
        enterParser("InnerExpr");
        AspInnerExpr aie = new AspInnerExpr(s.curLineNum());

        skip(s, leftParToken);

        aie.exp = AspExpr.parse(s);

        skip(s, rightParToken);

        leaveParser("InnerExpr");
        return aie;
    }

    //Se forklaring for prettyPrint() i AspSyntax
    @Override
    void prettyPrint(){
        Main.log.prettyWrite("( ");
        exp.prettyPrint();
        Main.log.prettyWrite(") ");
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return exp.eval(curScope);
    }
}
