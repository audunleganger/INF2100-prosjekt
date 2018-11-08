package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.*;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.runtime.*;



class AspFactor extends AspSyntax{
    ArrayList<AspFactorOpr> factoro = new ArrayList<>();
    ArrayList<AspPrimary> prim = new ArrayList<>();
    ArrayList<AspFactorPrefix> factorp = new ArrayList<>();
    ArrayList<Integer> prifixpos = new ArrayList<>();

    AspFactor(int n){
        super(n);
    }

    // Sjekker om vi jobber med en prefix eller en operator mellom faktorer,
    // og parser det via sin respektive parse-metode
    static AspFactor parse(Scanner s){
        enterParser("Factor");

        AspFactor af = new AspFactor(s.curLineNum());
        int teller = 0;
        while(true) {
            if(s.isFactorPrefix()){
                af.factorp.add(AspFactorPrefix.parse(s));
                af.prifixpos.add(teller);
            }
            af.prim.add(AspPrimary.parse(s));
            if(s.isFactorOpr()){
                af.factoro.add(AspFactorOpr.parse(s));
                teller++;
            }
            else{
                break;
            }
        }
        leaveParser("Factor");

        return af;
    }

    //Se forklaring for prettyPrint() i AspSyntax
    @Override
    void prettyPrint() {
        int teller = 0;

        if(factorp.isEmpty() && prim.size() == 1){
            prim.get(0).prettyPrint();
        }
        else{
            for(int a = 0; a<prim.size(); a++) {
                if(a < prifixpos.size() && a == prifixpos.get(teller)) {
                    factorp.get(teller).prettyPrint();
                    teller++;
                }
                prim.get(a).prettyPrint();

                if(a != factoro.size()) {
                    factoro.get(a).prettyPrint();
                }
            }
        }

    }

    // IKKE FERDIG
    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        int teller = 0;
        int pos = prim.size();
        RuntimeValue v = prim.get(0).eval(curScope);
        for (int i = 1; i < prim.size(); i++) {
            if(prefixNegav(teller, i - 1)){
                v = v.evalNegate(this);
                teller++;
            }
            String k = factoro.get(i-1).getSign();
            switch(k) {
                case "* ":
                    v = v.evalMultiply(prim.get(i).eval(curScope), this);
                    break;
                case "/ ":
                    v = v.evalDivide(prim.get(i).eval(curScope), this);
                    break;
                case "% ":
                    v = v.evalModulo(prim.get(i).eval(curScope), this);
                    break;
                case "// ":
                    v = v.evalIntDivide(prim.get(i).eval(curScope), this);
                    break;
                default:
                    Main.panic("Illegal factor operator: " + k + "!");
            }
        }
        if ((teller == 0 || teller < factorp.size()) && prefixNegav(teller,pos - 1)){
            v = v.evalNegate(this);
        }
        return v;
    }

    private boolean prefixNegav(int teller, int pos) {
        if(prifixpos.isEmpty()){
            return false;
        }
        else if(pos == prifixpos.get(teller)){
            return (factorp.get(teller).getSign().equals("-"));
        }
        return false;
    }
}
