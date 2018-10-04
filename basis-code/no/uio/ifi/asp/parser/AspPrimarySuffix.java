package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.*;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;


abstract class AspPrimarySuffix extends AspSyntax{

    AspPrimarySuffix(int n){
        super(n);
    }

    static AspPrimarySuffix parse(Scanner s){
        enterParser("Primary Suffix");

        AspPrimarySuffix aps = null;

        if(s.curToken().kind == leftParToken){
            aps = AspArguments.parse(s);
        }
        else if(s.curToken().kind == leftBracketToken){
            aps = AspSubscription.parse(s);
        }
        else{
            parserError("Expected an Primary Suffix expression but found a " + s.curToken().kind + "!" + s.curLineNum());
        }

        leaveParser("Primary Suffix");

        return aps;
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
