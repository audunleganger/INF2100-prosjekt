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

        skip(s, rightParToken);

        leaveParser("Arguments");

        return aa;
    }

    @Override
    void prettyPrint(){
        int amount_p = expr.size() - 1;
        Main.log.prettyWrite(" (");
        if(expr.size() == 1){
            expr.get(0).prettyPrint();
        }
        else{
            for(AspExpr ee : expr){
                ee.prettyPrint();
                if(amount_p != 0){
                    Main.log.prettyWrite(", ");
                    amount_p--;
                }
            }
        }
        Main.log.prettyWrite(") ");
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 3:
        return null;
    }
}
