package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.*;
import no.uio.ifi.asp.main.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.runtime.*;



class AspName extends AspAtom{
    String word;

    AspName(int n){
        super(n);
    }

    static AspName parse(Scanner s) {
        enterParser("Name");

        AspName an = new AspName(s.curLineNum());
        an.word = s.curToken().kind.toString();
        skip(s,nameToken);

        leaveParser("Name");

        return an;
    }

    @Override
    void prettyPrint() {
        word = " " + word + " ";
        Main.log.prettyWrite(word);
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 3:
        return null;
    }
}
