package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;

import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspProgram extends AspSyntax {

    ArrayList<AspStmt> stmts = new ArrayList<>();

    AspProgram(int n) {
        super(n);
    }


    public static AspProgram parse(Scanner s) {
        enterParser("program");

        AspProgram ap = new AspProgram(s.curLineNum());
        while (s.curToken().kind != eofToken) {
            if(s.curToken().kind == nameToken){
                if(s.anyEqualToken()){
                    ap.stmts.add(AspAssignment.parse(s));
                }
                else{
                    ap.stmts.add(AspExprStmt.parse(s));
                }
            }
            else{
                // all others, for now random shit:
                ap.stmts.add(AspExprStmt.parse(s));
            }
        }

        leaveParser("program");
        return ap;
    }


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
