package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.*;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.runtime.*;



class AspExprStmt extends AspStmt{
    AspExpr expr;

    AspExprStmt(int s){
        super(s);
    }

    // Parser innholdet fra scanner via AspExpr sin parse-metode, og
    // lagrer resultatet i variabelen expr
    static AspExprStmt parse(Scanner s){
        enterParser("Exp Stmt");

        AspExprStmt aes = new AspExprStmt(s.curLineNum());

        aes.expr = AspExpr.parse(s);

        skip(s,newLineToken);

        leaveParser("Exp Stmt");

        return aes;
    }

    //Se forklaring for prettyPrint() i AspSyntax
    @Override
    void prettyPrint(){
        expr.prettyPrint();
        Main.log.prettyWriteLn(" \n");
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 3:
        return null;
    }
}
