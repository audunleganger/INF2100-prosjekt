package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.*;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.runtime.*;



class AspFactorOpr extends AspSyntax{
    String sign;

    AspFactorOpr(int n) {
        super(n);
    }

    // Sjekker hva slags faktor-operator vi jobber med, og setter variabelen sign
    // til denne operatoren. Skipper deretter denne tokenen
    static AspFactorOpr parse(Scanner s){
        enterParser("Factor Opr");

        AspFactorOpr afo = new AspFactorOpr(s.curLineNum());
        switch (s.curToken().kind) {
            case astToken: afo.sign = "* "; skip(s, astToken);  break;
            case slashToken: afo.sign = "/ "; skip(s, slashToken); break;
            case percentToken: afo.sign = "% "; skip(s, percentToken); break;
            case doubleSlashToken: afo.sign = "// "; skip(s, doubleSlashToken); break;
        }

        leaveParser("Factor Opr");

        return afo;
    }

    //Se forklaring for prettyPrint() i AspSyntax
    @Override
    void prettyPrint() {
        Main.log.prettyWrite(sign);
    }

    // IKKE FERDIG!
    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 3:
        /*
        RuntimeValue v = null;
        if (sign.equals("* "))    {
            v = v.evalMultiply(this);
        }
        else if (sign.equals("/ "))    {
            v = v.evalDivide(this);
        }
        else if (sign.equals("% "))    {
            v = v.evalModulo(this);
        }
        else if (sign.equals("// "))    {
            v = v.evalIntDivide(this);
        }
        else    {
            Main.panic("Illegal factor operator: " + sign + "!");
        }
        return v;
        */
        return null;
    }
}
