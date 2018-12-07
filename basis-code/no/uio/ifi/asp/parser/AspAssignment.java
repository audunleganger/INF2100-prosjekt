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

    /*
    Foerst henter metoden ut variabelnavnet. Deretter sjekker vi om vi har
    en enkelt tilordning, eller en med flere elementer (liste/dictionary).
    Har vi en med flere elementer, sjekker vi hva slags type liste det er, for saa
    aa tilordne denne listen til skopet via skopet sin assign-funksjon.
    Har vi kun et enkelt element, blir de
    */
    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        String aName = name.word;
        RuntimeValue k = null;
        trace("Assignment of variable " + aName);
        if(!lasub.isEmpty()) {
            k = lasub.get(0).eval(curScope);
            RuntimeValue aExpr = exp.eval(curScope);
            RuntimeValue list_to_as = name.eval(curScope);

            list_to_as.evalAssignElem(k, aExpr,this);
            trace("Assignment of variable " + aName + " to be a dictionary or list");
            if(list_to_as instanceof  RuntimeDictionaryValue) {
                curScope.assign(aName, new RuntimeDictionaryValue(
                        list_to_as.getListValues("Append", this)));
                return null;
            }
            else{
                curScope.assign(aName, new RuntimeListValue(
                        list_to_as.getListValues("Append", this)));
                return null;
            }
        }
        RuntimeValue aExpr = exp.eval(curScope);
        trace("Assignment of variable " + aName + " = " + aExpr.toString());
        curScope.assign(aName, aExpr);
        return null;
    }
}
