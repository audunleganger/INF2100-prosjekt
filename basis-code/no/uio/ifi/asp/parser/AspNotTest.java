package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.*;
import no.uio.ifi.asp.main.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.runtime.*;


class AspNotTest extends AspSyntax{
    AspComparison comp;
    Boolean hasNot = false;

    AspNotTest(int n){
        super(n);
    }

    // Skipper notToken hvis den finner en. Parser foelgende
    // sammenlikning via AspComparison sin parse-metode
    static AspNotTest parse(Scanner s) {
        enterParser("Not test");
        AspNotTest ant = new AspNotTest(s.curLineNum());

        if(s.curToken().kind == notToken) {
            ant.hasNot = true;
            skip(s, notToken);
        }
        ant.comp = AspComparison.parse(s);

        leaveParser("Not test");

        return ant;
    }

    //Se forklaring for prettyPrint() i AspSyntax
    @Override
    void prettyPrint() {
        if(hasNot == true){
            Main.log.prettyWrite("not ");
        }
        comp.prettyPrint();
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        RuntimeValue v = comp.eval(curScope)
            if (hasNot){
                v = v.evalNot(this)
            }
        return v;
    }
}
