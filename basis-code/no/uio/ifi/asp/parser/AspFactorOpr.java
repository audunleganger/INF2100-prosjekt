package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.*;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.runtime.*;



class AspFactorOpr extends AspSyntax{
    static String sign;

    AspFactorOpr(int n) {
        super(n);
    }

    static AspFactorOpr parse(Scanner s){
        enterParser("Factor Opr");

        AspFactorOpr afo = new AspFactorOpr(s.curLineNum());

    
        leaveParser("Factor Opr");

        return afo;
    }

    @Override
    void prettyPrint() {
        Main.log.prettyWrite(sign);
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 3:
        return null;
    }
}
