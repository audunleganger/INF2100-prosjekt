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
            int teller = 0;
            for(AspTerm at : term){
                at.prettyPrint();
                if(teller < compo.size())
                    compo.get(teller).prettyPrint();
                    teller++;
            }
        }
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        RuntimeValue v = term.get(0).eval(curScope);
        int comp_teller = 0;
        int term_teller = 0;
        while(true) {
            if (compo.isEmpty()) {
                break;
            }
            if(comp_teller < compo.size()) {
                String comp = compo.get(comp_teller).sign;
                RuntimeValue x = term.get(term_teller).eval(curScope);
                RuntimeValue y = term.get(term_teller + 1).eval(curScope);
                RuntimeValue answear;

                switch (comp){
                    case "< ": answear = x.evalLess(y,this);
                    if(answear.getBoolValue("comp", this)){
                        break;
                    }
                    return answear;
                    case "> ": answear = x.evalGreater(y, this);
                    if (answear.getBoolValue("comp", this)) {
                        break;
                    }
                    return answear;
                    case "== ": answear = x.evalEqual(y, this);
                        if (answear.getBoolValue("comp", this)) {
                            break;
                        }
                        return answear;
                    case ">= ": answear = x.evalGreaterEqual(y, this);
                        if (answear.getBoolValue("comp", this)) {
                            break;
                        }
                        return answear;
                    case "<= ": answear = x.evalLessEqual(y, this);
                        if (answear.getBoolValue("comp", this)) {
                            break;
                        }
                        return answear;
                    case "!= ": answear = x.evalNotEqual(y, this);
                        if (answear.getBoolValue("comp", this)) {
                            break;
                        }
                        return answear;
                    default: System.out.println("Something went wrong comparision"); System.exit(1); break;
                }
                term_teller ++;
                comp_teller ++;
            }
            else {
                return new RuntimeBoolValue(true);
            }
        }

        return v;
    }
}
