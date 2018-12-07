package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.*;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.runtime.*;



class AspIfStmt extends AspStmt{
    ArrayList<AspExpr> expr = new ArrayList<>();
    ArrayList<AspSuite> suite = new ArrayList<>();
    AspSuite suite2;


    AspIfStmt(int n)    {
        super(n);
    }

    // Parser et expression via AspExpr sin parse-metode, og en korresponderende kodeblokk via
    // AspSuite sin parse-metode. Vil deretter gjenta for alle (potensielle) elif-er.
    // Til slutt vil metoden sjekke om et else-statement eksisterer. Hvis det gjoer dette,
    // vil den foelgende kodeblokken parses via AspSuite sin parse-metode
    static AspIfStmt parse(Scanner s)   {
        enterParser("IfStmt");

        AspIfStmt ais = new AspIfStmt(s.curLineNum());

        skip(s, ifToken);

        while(true){
            ais.expr.add(AspExpr.parse(s));
            skip(s, colonToken);
            ais.suite.add(AspSuite.parse(s));
            if(s.curToken().kind != elifToken){
                break;
            }
            skip(s, elifToken);
        }
        if(s.curToken().kind == elseToken){
            skip(s, elseToken);
            skip(s, colonToken);
            ais.suite2 = AspSuite.parse(s);
        }
        else{
            ais.suite2 = null;
        }

        leaveParser("IfStmt");

        return ais;

    }

    //Se forklaring for prettyPrint() i AspSyntax
    @Override
    void prettyPrint(){
        int amount_p = expr.size() - 1;
        Main.log.prettyWrite("if ");

        if(suite.size() == 1){
            expr.get(0).prettyPrint();
            Main.log.prettyWrite(": ");
            suite.get(0).prettyPrint();

            if(suite2 != null){
                Main.log.prettyWrite("else ");
                Main.log.prettyWrite(": ");
                suite2.prettyPrint();
            }
        }
        else{
            int teller = 0;
            for(AspExpr ae : expr){
                ae.prettyPrint();
                Main.log.prettyWrite(": ");
                suite.get(teller).prettyPrint();
                teller++;
                if(amount_p != 0){
                    Main.log.prettyWrite("elif ");
                    amount_p--;
                }
            }
            if(suite2 != null){
                Main.log.prettyWrite("else ");
                Main.log.prettyWrite(": ");
                suite2.prettyPrint();
            }
        }

    }

    // Metoden sjekker hva slags struktur if/elif/else-statementet har, og evaluerer
    // det via suite sin eval-metode.
    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        if (suite2 == null) {
            // Kommer vi hit, har vi ingen else-statement
            if(suite.size() == 1){
                // Kommer vi hit, har vi kun et if-statement
                RuntimeValue v = expr.get(0).eval(curScope);
                if(v.getBoolValue("If test",this)) {
                    trace("If true: ..");
                    v = suite.get(0).eval(curScope);
                    return v;
                }
                else{
                    trace("if false:");
                    return null;
                }
            }
            else {
                // Kommer vi hit, vil vi iterere gjennom alle elif-ene og evaluere dem
                int teller = 0;
                trace("elif true...");
                for (AspExpr ae : expr) {
                    if(ae.eval(curScope).getBoolValue("if test",this)){
                        return suite.get(teller).eval(curScope);
                    }
                    teller ++;
                }
                trace("elif false:");
                return null;
            }
        }
        else {
            // Kommer vi hit, har vi et else-statement
            if(suite.size() == 1){
                trace("if true: ..");
                RuntimeValue v = expr.get(0).eval(curScope);
                if(v.getBoolValue("If test",this)) {
                    v = suite.get(0).eval(curScope);
                    return v;
                }
                else{
                    trace("else:");
                    return suite2.eval(curScope);
                }
            }
            else {
                int teller = 0;
                trace("elif true: ..");
                for (AspExpr ae : expr) {
                    if(ae.eval(curScope).getBoolValue("if test",this)){
                        return suite.get(teller).eval(curScope);
                    }
                    teller ++;
                }
                trace("else:");
                return suite2.eval(curScope);
            }
        }
    }
}
