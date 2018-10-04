package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.*;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.runtime.*;



abstract class AspStmt extends AspSyntax{
    AspStmt(int n){
        super(n);
    }

    static AspStmt parse(Scanner s){
        enterParser("Stmt");

        AspStmt as = null;

        if(s.curToken().kind == nameToken){
            if(s.anyEqualToken()){
                as = AspAssignment.parse(s);
            }
            else{
                as = AspExprStmt.parse(s);
            }
        }
        else{
            // all others, for now random shit:
            as = AspExprStmt.parse(s);
        }

        leaveParser("Stmt");

        return as;
    }

    @Override
    void prettyPrint() {

    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 4:
        return null;
    }
}
