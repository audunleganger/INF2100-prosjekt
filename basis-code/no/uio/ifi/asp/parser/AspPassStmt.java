package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.*;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.runtime.*;



class AspPassStmt extends AspStmt{

    AspPassStmt(int n)  {
        super(n);
    }

    // Skipper passToken og newLineToken
    static AspPassStmt parse(Scanner s){
        enterParser("Pass Stmt");

        AspPassStmt aps = new AspPassStmt(s.curLineNum());

        skip(s, passToken);
        skip(s, newLineToken);

        leaveParser("Pass Stmt");

        return aps;
    }

    //Se forklaring for prettyPrint() i AspSyntax
    @Override
    void prettyPrint(){
        Main.log.prettyWrite("pass ");
        Main.log.prettyWriteLn("\n");
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        trace("pass");
        return null;
    }
}
