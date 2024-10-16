package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.*;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.runtime.*;



class AspArguments extends AspPrimarySuffix{
    ArrayList<AspExpr> expr = new ArrayList<>();

    AspArguments(int n){
        super(n);
    }

    // Legger til en AspExpr for hvert argument, og avslutter naar vi ikke har flere
    // argumenter (finner noe annet enn en komma-token etter et argument). Vi parser disse
    // ved hjelp av AspExpr sin parse-metode
    static AspArguments parse(Scanner s){
        enterParser("Arguments");

        AspArguments aa = new AspArguments(s.curLineNum());

        skip(s, leftParToken);

        while(true) {
            if(s.curToken().kind == rightParToken){
                break;
            }
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

    //Se forklaring for prettyPrint() i AspSyntax
    @Override
    void prettyPrint(){
        int amount_p = expr.size() - 1;
        Main.log.prettyWrite("( ");
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
        ArrayList<RuntimeValue> many_exp = new ArrayList<>();

        for(AspExpr ae : expr) {
            many_exp.add(ae.eval(curScope));
        }

        return new RuntimeListValue(many_exp);
    }
}
