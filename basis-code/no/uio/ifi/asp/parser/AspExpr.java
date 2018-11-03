package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.runtime.*;


public class AspExpr extends AspSyntax {
    ArrayList<AspAndTest> andTests = new ArrayList<>();

    AspExpr(int n) {
        super(n);
    }

    // Legger til en AspAndTest helt til den ikke finner en orToken, og
    // parser disse via AspAndTest sin parse-metode
    public static AspExpr parse(Scanner s) {
        enterParser("Expr");
        AspExpr ae = new AspExpr(s.curLineNum());

        while (true){
            ae.andTests.add(AspAndTest.parse(s));
            if (s.curToken().kind != orToken) {
                break;
            }
            skip(s, orToken);
        }

        leaveParser("Expr");
        return ae;
    }

    //Se forklaring for prettyPrint() i AspSyntax
    @Override
    public void prettyPrint() {
        int nPrinted = 0;
        for (AspAndTest aat : andTests) {
            if (nPrinted > 0){
                Main.log.prettyWrite("or ");
            }
            aat.prettyPrint();
            nPrinted++;
        }
    }


    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        RuntimeValue v = andTests.get(0).eval(curScope);
        for (int i = 1; i < andTests.size(); i++)   {
            v = andTests.get(i).eval(curScope);
        }
        return v;
    }
}
