package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.*;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.runtime.*;



class AspTermOpr extends AspSyntax{
    String pluss;
    String minus;

    AspTermOpr(int n){
        super(n);
    }

    static AspTermOpr parse(Scanner s){
        enterParser("Term Opr");

        AspTermOpr ato = new AspTermOpr(s.curLineNum());

        switch (s.curToken().kind) {
            case plusToken: ato.pluss = "+"; ato.minus = "";
            skip(s, plusToken); break;
            case minusToken: ato.minus = "-"; ato.pluss = "";
            skip(s, minusToken); break;
        }

        leaveParser("Term Opr");

        return ato;
    }

    @Override
    void prettyPrint(){
        if(pluss == "+"){
            Main.log.prettyWrite("+ ");
        }
        else{
            Main.log.prettyWrite("- ");
        }
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 3:
        return null;
    }
}
