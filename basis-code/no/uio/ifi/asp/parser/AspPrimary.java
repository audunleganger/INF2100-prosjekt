package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.*;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.runtime.*;



class AspPrimary extends AspSyntax{
    AspAtom atom;
    ArrayList<AspPrimarySuffix> primaryS = new ArrayList<>();

    AspPrimary(int n) {
        super(n);
    }

    // Parser et atom via AspAtom sin parse-metode. Vil deretter parse foelgende
    // primaries via AspPrimarySuffix sin parse-metode. Stopper naar den finner en ( eller [
    static AspPrimary parse(Scanner s) {
        enterParser("Primary");

        AspPrimary ap = new AspPrimary(s.curLineNum());

        ap.atom = AspAtom.parse(s);

        while(true) {

            if(s.curToken().kind != leftParToken && s.curToken().kind != leftBracketToken){
                break;
            }
            ap.primaryS.add(AspPrimarySuffix.parse(s));
        }

        leaveParser("Primary");

        return ap;
    }

    //Se forklaring for prettyPrint() i AspSyntax
    @Override
    void prettyPrint(){
        if(primaryS.isEmpty()){
            atom.prettyPrint();
        }
        else{
            atom.prettyPrint();
            for(AspPrimarySuffix aps : primaryS){
                aps.prettyPrint();
            }
        }
    }

    /*
    Metoden sjekker om vi har suffixer etter primariene, og evaluerer resultatet
    ut i fra dette. Hvis vi har det, vil den sjekke om vi oprerer med en liste/dictionary,
    eller med et funksjonskall, for saa aa kalle paa enten evalSubscription (for lister/dictionaries)
    eller evalFuncCall (for funksjonskall). Har vi ikke det, blir verdien sendt videre til atom.eval()
    */
    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        RuntimeValue v;
        RuntimeValue k = null;

        if(!primaryS.isEmpty()){
            k = primaryS.get(0).eval(curScope);
        }

        if (k != null){
            v = atom.eval(curScope);
            if (v instanceof RuntimeListValue || v instanceof  RuntimeDictionaryValue){
                trace("getting " + v.evalSubscription(k,this).toString() + " from a list or dictionary");
               v = v.evalSubscription(k,this);
            }
            else if(v instanceof RuntimeFunc){
                ArrayList<RuntimeValue> args = k.getListValues("Get list", this);
                trace("Call " + ((RuntimeFunc)v).getWord() + " with params: " + k.toString());
                v = v.evalFuncCall(args,this);
            }
        }
        else{
            v = atom.eval(curScope);
        }

        return v;
    }

}
