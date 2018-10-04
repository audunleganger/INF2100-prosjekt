package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.*;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;


class AspDictDisplay extends AspAtom{
    ArrayList<AspStringLiteral> strlit = new ArrayList<>();
    ArrayList<AspExpr> expr = new ArrayList<>();

    AspDictDisplay(int n){
        super(n);
    }

    static AspDictDisplay parse(Scanner s){
        enterParser("DictDisplay");

        AspDictDisplay ads = new AspDictDisplay(s.curLIneNum());

        skip(s.leftBraceToken());
        while (true)    {
            aa.strlit.add(AspStringLiteral.parse(s));
            skip(s, colonToken);
            aa.expr.add(AspExpr.parse(s));
            if (s.curToken().kind != commaToken)    {
                break;
            }
        }
        return null;
    }

    @Override
    void prettyPrint(){
        //Does nothing now
    }
}
