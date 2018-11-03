package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.*;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.runtime.*;



class AspComparison extends AspSyntax{
    ArrayList<AspTerm> term = new ArrayList<>();
    ArrayList<AspCompOpr> compo = new ArrayList<>();

    AspComparison(int n){
        super(n);
    }

    // Legger til AspTerm og AspCompOp annenhver gang helt til det ikke er flere operatorer,
    // og parser disse via sine respektive parse-metoder.
    static AspComparison parse(Scanner s) {
        enterParser("Comparison");

        AspComparison ac = new AspComparison(s.curLineNum());

        while(true) {
            ac.term.add(AspTerm.parse(s));

            if(s.isCompOpr()){
                ac.compo.add(AspCompOpr.parse(s));
            }
            else{
                break;
            }
        }
        leaveParser("Comparison");

        return ac;
    }

    //Se forklaring for prettyPrint() i AspSyntax
    @Override
    void prettyPrint() {
        if(term.size() == 1){
            term.get(0).prettyPrint();
        }
        else{
            for(AspTerm at : term){
                at.prettyPrint();
                if(!compo.isEmpty()){
                    compo.get(0).prettyPrint();
                    compo.remove(0);
                }
            }
        }
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        RuntimeValue v = term.get(0).eval(curScope);
        for (int i = 1; i < term.size(); i++)   {
            TokenKind k = compo.get(i-1).kind;
            switch (k) {
                case lessToken:
                    v = v.evalLess(factors.get(i).eval(curScope), this); break;
                case greaterToken:
                    v = v.evalGreater(factors.get(i).eval(curScope), this); break;
                case doubleEqualToken:
                    v = v.evalEqual(factors.get(i).eval(curScope), this); break;
                case greaterEqualToken:
                    v = v.evalGreaterEqual(factors.get(i).eval(curScope), this); break;
                case lessEqualToken:
                    v = v.evalLessEqual(factors.get(i).eval(curScope), this); break;
                case notEqualToken:
                    v = v.evalNotEqual(factors.get(i).eval(curScope), this); break;
                default:
                    Main.panic("Illegal term operator: " + k + "!");
            }
        }
        return v;
    }
}
