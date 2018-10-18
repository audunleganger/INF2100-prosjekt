package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.*;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.runtime.*;



class AspReturnStmt extends AspStmt{
    AspExpr expr;

    AspReturnStmt(int n)    {
        super(n);
    }

    // Leser uttrykket som skal returneres via AspExpr sin parse-metode.
    // Vil deretter skippe den foelgende newLineTokenen
    static AspReturnStmt parse(Scanner s) {
        enterParser("Return Stmt");

        AspReturnStmt ars = new AspReturnStmt(s.curLineNum());

        skip(s, returnToken);
        ars.expr = AspExpr.parse(s);
        skip(s, newLineToken);

        leaveParser("Return Stmt");

        return ars;

    }

    //Se forklaring for prettyPrint() i AspSyntax
    @Override
    void prettyPrint(){
        Main.log.prettyWrite("return ");
        expr.prettyPrint();
        Main.log.prettyWriteLn("\n");
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 3:
        return null;
    }
}
