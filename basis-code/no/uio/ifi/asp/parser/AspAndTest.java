package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.*;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.runtime.*;



class AspAndTest extends AspSyntax{

    ArrayList<AspNotTest> notTests = new ArrayList<>();

    AspAndTest(int n) {
         super(n);
     }

     // Legger til en AspNotTest for hver andToken, og parser disse 
     // via AspNotTest sin parse-metode
    static AspAndTest parse(Scanner s) {
         enterParser("And test");
         AspAndTest aat = new AspAndTest(s.curLineNum());

         while (true) {
             aat.notTests.add(AspNotTest.parse(s));
             if (s.curToken().kind != andToken) {
                 break;
             }
             skip(s, andToken);
         }

         leaveParser("and test");

         return aat;
    }

    //Se forklaring for prettyPrint() i AspSyntax
    @Override
    void prettyPrint() {
        int nPrinted = 0;
        for (AspNotTest ant: notTests) {
            if (nPrinted > 0) {
                Main.log.prettyWrite("and ");
            }
            ant.prettyPrint();
            nPrinted++;
        }
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        RuntimeValue v = notTests.get(0).eval(curScope);
        for (int i = 1 < notTests.size(); i++) {
            if (!v.getBoolValue("and oeprand", this)) {
                return v;
            }
            c = notTests.get(i).eval(curScope);
        }
        return v;
    }
}
