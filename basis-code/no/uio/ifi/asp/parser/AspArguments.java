package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.*;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;


class AspArguments extends AspPrimarySuffix{
    ArrayList<AspExpr> expr = new ArrayList<>();

    AspArguments(int n){
        super(n);
    }

    static AspArguments parse(Scanner s){
        enterParser("Arguments");

        AspArguments aa = new AspArguments(s.curLineNum());

        skip(s, leftParToken);

        while(true) {
            aa.expr.add(AspExpr.parse(s));
            if(s.curToken().kind != commaToken){
                break;
            }
            skip(s, commaToken);
        }

        if(s.curToken().kind != rightParToken){
            
        }
    }

}
