package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import no.uio.ifi.asp.runtime.*;


import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspProgram extends AspSyntax {

    ArrayList<AspStmt> stmts = new ArrayList<>();

    AspProgram(int n) {
        super(n);
    }

    // Leser og parser hvert statement via AspStmt sin
    // parse-metode helt til den finner eofToken
    public static AspProgram parse(Scanner s) {
        enterParser("program");

        AspProgram ap = new AspProgram(s.curLineNum());
        while (s.curToken().kind != eofToken) {
            ap.stmts.add(AspStmt.parse(s));
        }

        leaveParser("program");
        return ap;
    }

    //Se forklaring for prettyPrint() i AspSyntax
    @Override
    public void prettyPrint() {
        for ( AspStmt aStmt : stmts){
            aStmt.prettyPrint();
        }
    }


    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 4:
        return null;
    }
}
