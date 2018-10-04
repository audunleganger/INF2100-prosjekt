package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.*;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;


abstract class AspPrimarySuffix extends AspSyntax{
    AspArgumments arg;
    AspSubscription sub;

    AspPrimarySuffix(int n){
        super(n);
    }

    static AspPrimarySuffix parse(Scanner s){
        enterParser("Primary Suffix");

        AspPrimarySuffix aps = new AspPrimarySuffix(s.curLineNum());

        if(s.curToken().kind == leftParToken){
            aps.arg = AspArgumments.parse(s);
            aps.sub = null;
        }
        else if(s.curToken().kind == leftBracketToken){
            aps.sub = AspSubscription.parse(s);
            aps.arg = null
        }
        else(
            parserError("Expected an Primary Suffix expression but found a " + s.curToken().kind + "!", s.curLineNum());
        )

        leaveParser("Primary Suffix");

        return aps
    }

    @Override
    void prettyPrint(){
        if(sub == null){
            arg.prettyPrint();
        }
        else{
            sub.prettyPrint();
        }
    }
}
