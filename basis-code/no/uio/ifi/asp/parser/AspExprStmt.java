package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.*;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;


class AspExprStmt extends AspStmt{
    AspExpr expr;

    AspExprStmt(int s){
        super(s);
    }

    static AspExprStmt parse(Scanner s){
        enterParser("Exp Stmt");

        AspExprStmt aes = new AspExprStmt(s.curLineNum());

        aes.expr = AspExpr.parse(s);

        skip(s,newLineToken);

        leaveParser("Exp Stmt");

        return aes;
    }

    @Override
    void prettyPrint(){
        expr.prettyPrint();
        Main.log.prettyWriteLn(" \n");
    }
}
