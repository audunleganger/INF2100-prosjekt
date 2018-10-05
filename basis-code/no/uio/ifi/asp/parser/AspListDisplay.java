package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.*;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.runtime.*;



class AspListDisplay extends AspAtom{
    ArrayList<AspExpr> expr = new ArrayList<>();

    AspListDisplay(int n){
        super(n);
    }

    static AspListDisplay parse(Scanner s){
        enterParser("ListDisplay");

        AspListDisplay ald = new AspListDisplay(s.curLineNum());

        skip(s, leftBracketToken);

        while(true) {
            ald.expr.add(AspExpr.parse(s));
            if (s.curToken().kind != commaToken)    {
                break;
            }
            skip(s, commaToken);
        }

        skip(s, rightBracketToken);

        leaveParser("ListDisplay");

        return ald;
    }

    @Override
    void prettyPrint(){
        int amount_p = expr.size() - 1;
        Main.log.prettyWrite("[ ");
        if (expr.size() == 1)   {
            expr.get(0).prettyPrint();
        }
        else    {
            for (AspExpr ae : expr)  {
                ae.prettyPrint();
                if (amount_p != 0)  {
                    Main.log.prettyWrite(", ");
                    amount_p--;
                }
            }
        }
        Main.log.prettyWrite("] ");
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 3:
        return null;
    }
}
