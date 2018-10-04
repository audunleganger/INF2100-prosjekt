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

    @Override
    void prettyPrint() {
        int nPrinted = 0;
        for (AspNotTest ant: notTests) {
            if (nPrinted > 0) {
                Main.log.prettyWrite(" and ");
            }
            ant.prettyPrint();
            nPrinted++;
        }
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 3:
        return null;
    }
}
