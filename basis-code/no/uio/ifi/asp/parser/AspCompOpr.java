package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.*;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.runtime.*;



class AspCompOpr extends AspSyntax{
     String sign;

    AspCompOpr(int n){
        super(n);
    }

    static AspCompOpr parse(Scanner s){
        enterParser("Comparison Operator");
        AspCompOpr aco = new AspCompOpr(s.curLineNum());
        if (s.curToken().kind == lessToken)   {
            skip(s, lessToken);
            aco.sign = "< ";
        }
        else if (s.curToken().kind == greaterToken) {
            skip(s, greaterToken);
            aco.sign = "> ";
        }
        else if (s.curToken().kind == doubleEqualToken)   {
            skip(s, doubleEqualToken);
            aco.sign = "== ";
        }
        else if (s.curToken().kind == greaterEqualToken)    {
            skip(s, greaterEqualToken);
            aco.sign = ">= ";
        }
        else if (s.curToken().kind == lessEqualToken)   {
            skip(s, lessEqualToken);
            aco.sign = "<= ";
        }
        else if (s.curToken().kind == notEqualToken)    {
            skip(s, notEqualToken);
            aco.sign = "!= ";
        }
        leaveParser("Comparison Operator");
        return aco;
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
