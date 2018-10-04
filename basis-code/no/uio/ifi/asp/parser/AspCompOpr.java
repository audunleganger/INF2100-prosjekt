package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.*;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.runtime.*;



class AspCompOpr extends AspSyntax{
    static String sign;

    AspCompOpr(int n){
        super(n);
    }

    static AspCompOpr parse(Scanner s){
        enterParser("Comparison Operator");
        AspCompOpr aco = new AspCompOpr(s.curLineNum());
        if (s.curToken().kind == lessToken)   {
            skip(s, lessToken);
            sign = "<";
        }
        else if (s.curToken().kind == greaterToken) {
            skip(s, greaterToken);
            sign = ">";
        }
        else if (s.curToken().kind == equalToken)   {
            skip(s, equalToken);
            sign = "==";
        }
        else if (s.curToken().kind == greaterEqualToken)    {
            skip(s, greaterEqualToken);
            sign = ">=";
        }
        else if (s.curToken().kind == lessEqualToken)   {
            skip(s, lessEqualToken);
            sign = "<=";
        }
        else if (s.curToken().kind == notEqualToken)    {
            skip(s, notEqualToken);
            sign = "!=";
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
