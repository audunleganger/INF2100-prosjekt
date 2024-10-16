package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.*;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.runtime.*;



class AspDictDisplay extends AspAtom{
    ArrayList<AspStringLiteral> strlit = new ArrayList<>();
    ArrayList<AspExpr> expr = new ArrayList<>();

    AspDictDisplay(int n){
        super(n);
    }

    // Legger til en AspStringLiteral og en AspExpr parvis, slik
    // at det blir parvis (med strlit som peker til expr). Disse parses
    // med sine respektive parse-metoder.
    static AspDictDisplay parse(Scanner s){
        enterParser("DictDisplay");

        AspDictDisplay ads = new AspDictDisplay(s.curLineNum());

        skip(s, leftBraceToken);

        while (true)    {
            ads.strlit.add(AspStringLiteral.parse(s));
            skip(s, colonToken);
            ads.expr.add(AspExpr.parse(s));
            if (s.curToken().kind != commaToken)    {
                break;
            }
            skip(s, commaToken);
        }

        skip(s, rightBraceToken);

        leaveParser("DictDisplay");

        return ads;
    }

    //Se forklaring for prettyPrint() i AspSyntax
    @Override
    void prettyPrint(){
        int amount_p = strlit.size() - 1;
        if (amount_p > (expr.size() - 1))   {
            amount_p = (expr.size() - 1);
        }

        Main.log.prettyWrite("{ ");
        if (strlit.size() == 1) {
            strlit.get(0).prettyPrint();
            Main.log.prettyWrite(": ");
            expr.get(0).prettyPrint();
        }

        else    {
            for (int i = 0; i < strlit.size(); i++) {
                strlit.get(i).prettyPrint();
                Main.log.prettyWrite(": ");
                expr.get(i).prettyPrint();
                if (amount_p != 0)  {
                    // Mulig dette endres til "\n"
                    Main.log.prettyWrite(", ");
                    amount_p--;
                }
            }
        }
        Main.log.prettyWrite("} ");
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        ArrayList<RuntimeValue> dic = new ArrayList<>();
        for (int i = 0; i < strlit.size(); i++) {
            dic.add(strlit.get(i).eval(curScope));
            dic.add(expr.get(i).eval(curScope));
        }
        return new RuntimeDictionaryValue(dic);
    }
}
