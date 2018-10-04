package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.*;
import no.uio.ifi.asp.main.*;


class AspNotTest extends AspSyntax{
    AspComparison comp;
    Boolean hasNot = false;

    AspNotTest(int n){
        super(n);
    }

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

    @Override
    void prettyPrint() {
        if(hasNot == true){
            Main.log.prettyWrite(" not ");
        }
        comp.prettyPrint();
    }
}
