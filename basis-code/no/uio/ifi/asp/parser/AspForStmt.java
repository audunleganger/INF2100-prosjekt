package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.*;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.runtime.*;



class AspForStmt extends AspStmt{
    public AspName name;
    public AspExpr expr;
    public AspSuite suite;

    public AspForStmt(int n){
        super(n);
    }

    // Parser navn via AspName, uttrykket via AspExpr, og kodeblokken
    // via AspSuite sine respektive parsemetoder, og lagrer det i variablene
    // name, expr, og suite respektivt
    static AspForStmt parse(Scanner s){
        enterParser("For");

        AspForStmt afs = new AspForStmt(s.curLineNum());

        skip(s, forToken);
        afs.name = AspName.parse(s);
        skip(s, inToken);
        afs.expr = AspExpr.parse(s);
        skip(s, colonToken);
        afs.suite = AspSuite.parse(s);

        leaveParser("For");

        return afs;
    }

    //Se forklaring for prettyPrint() i AspSyntax
    @Override
    void prettyPrint(){
        Main.log.prettyWrite("for ");
        name.prettyPrint();
        Main.log.prettyWrite("in ");
        expr.prettyPrint();
        Main.log.prettyWrite(": ");
        suite.prettyPrint();
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        String aName = name.word;
        RuntimeValue aExpr = expr.eval(curScope);
        int teller = 0;
        while(teller < aExpr.size("expr",this)) {
            trace("for teller < aExpr.size..");
            if(aExpr instanceof RuntimeDictionaryValue){
                RuntimeValue ap = ((RuntimeDictionaryValue) aExpr).getKey(teller,this);
                curScope.assign(aName,aExpr.evalSubscription(ap,this));
            }
            else {
                curScope.assign(aName,aExpr.evalSubscription(new RuntimeIntValue(teller),this));
            }
            suite.eval(curScope);
            teller ++;
        }
        trace("for teller > aExpr.size");
        return null;
    }
}
