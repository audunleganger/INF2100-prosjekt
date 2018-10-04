package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.*;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.runtime.*;



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
            parserError(("Expected an Primary Suffix expression but found a " + s.curToken().kind + "!" ), s.curLineNum());
        }

        leaveParser("Primary Suffix");

        return aps;
    }

    @Override
    void prettyPrint(){
    }
    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 4:
        return null;
    }
}
