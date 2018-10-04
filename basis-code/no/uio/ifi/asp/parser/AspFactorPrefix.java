package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.*;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.runtime.*;



class AspFactorPrefix extends AspSyntax{
    static String sign;

    AspFactorPrefix(int n) {
        super(n);
    }

    static AspFactorPrefix parse(Scanner s){
<<<<<<< HEAD
        AspFactorPrefix afp = new AspFactorPrefix(s.curLineNum());
=======
        AspFactorPrefix afp = new AspFactorPrefix();
>>>>>>> 06f5b2c080accfc3f645e3f13c200d9b7fba9055
        enterParser("Factor Prefix");
        if (s.curToken().kind == plusToken) {
            sign = "+";
            skip(s, plusToken);
        }
        else if (s.curToken().kind == minusToken)   {
            sign = "-";
            skip(s, minusToken);
        }
        leaveParser("Factor Prefix");
        return afp;
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
