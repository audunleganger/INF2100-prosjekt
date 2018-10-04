package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.*;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.runtime.*;



class AspInnerExpr extends AspAtom{
    String word;


    AspInnerExpr(int n){
        super(n);
    }

    static AspInnerExpr parse(Scanner s){
        enterParser("InnerExpr");

        skip(s, leftParToken);

        AspInnerExpr aie = new AspInnerExpr(s.curLineNum());
        aie.word = s.curToken().stringLit;

        skip(s, rightParToken);

        return aie;
    }

    @Override
    void prettyPrint(){
        word = " (" + word + ") ";
        Main.log.prettyWrite(word);
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 3:
        return null;
    }
}
