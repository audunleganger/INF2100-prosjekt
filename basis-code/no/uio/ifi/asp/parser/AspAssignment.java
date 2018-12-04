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

    // Legger til et AspNavn, og AspSubscriptions (hvis de eksisterer)
    // og parser disse med sine respektive parse-metoder
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

    //Se forklaring for prettyPrint() i AspSyntax
    @Override
    void prettyPrint() {
        name.prettyPrint();

        for(AspSubscription asub : lasub){
            asub.prettyPrint();
        }
        Main.log.prettyWrite("= ");
        exp.prettyPrint();
        Main.log.prettyWriteLn("\n");
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        String aName = name.word;
        RuntimeValue k = null;

        if(!lasub.isEmpty()) {
            k = lasub.get(0).eval(curScope);
            RuntimeValue aExpr = exp.eval(curScope);
            RuntimeValue list_to_as = name.eval(curScope);

            list_to_as.evalAssignElem(k, aExpr,this);

            curScope.assign(aName, new RuntimeListValue(
                    list_to_as.getListValues("Append", this)));
            return null;
        }
        RuntimeValue aExpr = exp.eval(curScope);

        curScope.assign(aName, aExpr);
        return null;
    }
}
