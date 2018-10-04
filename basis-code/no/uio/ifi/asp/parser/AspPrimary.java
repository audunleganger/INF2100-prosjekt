package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.*;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;


class AspPrimary extends AspSyntax{
    AspAtom atom;
    ArrayList<AspPrimarySuffix> primaryS = new ArrayList<>();

    AspPrimary(int n) {
        super(n);
    }

    static AspPrimary parse(Scanner s) {
        enterParser("Primary");

        AspPrimary ap = new AspPrimary(s.curLineNum());

        ap.atom = AspAtom.parse(s);

        while(true) {
            if(s.curToken().kind != leftParToken && s.curToken().kind != leftBraceToken){
                break;
            }
            primaryS.add(AspPrimarySuffix.parse(s));
        }

        leaveParser("Primary");

        return ap;
    }

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

}
