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

    AspFactor(int n){
        super(n);
    }

    // Sjekker om vi jobber med en prefix eller en operator mellom faktorer,
    // og parser det via sin respektive parse-metode
    static AspFactor parse(Scanner s){
        enterParser("Factor");

        AspFactor af = new AspFactor(s.curLineNum());

        while(true) {
            if(s.isFactorPrefix()){
                af.factorp.add(AspFactorPrefix.parse(s));
            }
            af.prim.add(AspPrimary.parse(s));
            if(s.isFactorOpr()){
                af.factoro.add(AspFactorOpr.parse(s));
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
        if(factorp.isEmpty() && prim.size() == 1){
            prim.get(0).prettyPrint();
        }
        else{
            for(AspPrimary ap : prim){
                if(!factorp.isEmpty()){
                    factorp.get(0).prettyPrint();
                    factorp.remove(0);
                }
                ap.prettyPrint();
                if(!factoro.isEmpty()){
                    factoro.get(0).prettyPrint();
                    factoro.remove(0);
                }
            }
        }
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 3:
        return null;
    }
}
