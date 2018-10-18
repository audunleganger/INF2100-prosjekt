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

    // Lagrer innholdet fra den gjendelnde nameToken-en til variabelen word
    static AspName parse(Scanner s) {
        enterParser("Name");

        AspName an = new AspName(s.curLineNum());
        an.word = s.curToken().name;
        skip(s,nameToken);

        leaveParser("Name");

        return an;
    }

    //Se forklaring for prettyPrint() i AspSyntax
    @Override
    void prettyPrint() {
        word = word + " ";
        Main.log.prettyWrite(word);
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 3:
        return null;
    }
}
