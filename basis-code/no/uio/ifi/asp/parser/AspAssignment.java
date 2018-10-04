package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.*;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.runtime.*;



class AspAssignment extends AspStmt{
    AspName name;
    ArrayList<AspSubscription> lasub = new ArrayList<>();
    AspExpr exp;

    AspAssignment(int n){
        super(n);
    }

    static AspAssignment parse(Scanner s) {
        enterParser("Assignment");

        AspAssignment aa = new AspAssignment(s.curLineNum());
        aa.name = AspName.parse(s);

        while(true) {
            if(s.curToken().kind != leftBracketToken){
                break;
            }
            aa.lasub.add(AspSubscription.parse(s));
        }
        skip(s, equalToken);
        aa.exp = AspExpr.parse(s);
        skip(s, newLineToken);
        leaveParser("Assignment");

        return aa;
    }

    @Override
    void prettyPrint() {
        name.prettyPrint();

        for(AspSubscription asub : lasub){
            asub.prettyPrint();
        }
        Main.log.prettyWrite(" = ");
        exp.prettyPrint();
        Main.log.prettyWriteLn("\n");
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 3:
        return null;
    }
}
