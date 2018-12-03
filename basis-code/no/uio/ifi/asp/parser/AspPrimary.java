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

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        RuntimeValue v;
        ArrayList<RuntimeValue> k = new ArrayList<>();
        for (AspPrimarySuffix ap: primaryS){
            k.add(ap.eval(curScope));
        }
        if (!k.isEmpty()){
            v = atom.eval(curScope);
            if (v instanceof RuntimeListValue || v instanceof  RuntimeDictionaryValue){
                //for nå kan det være det etter på må byttes ut til noe annet, det er ikke riktig ennå.
                System.out.println("this part is not implemented");
            }
            else if(v instanceof RuntimeFunc){
                v = v.evalFuncCall(k,this);
            }
        }
        else{
            v = atom.eval(curScope);
        }

        return v;
    }

}
