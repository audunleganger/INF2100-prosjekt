package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.*;
import no.uio.ifi.asp.main.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.runtime.*;



class AspTermOpr extends AspSyntax{
    private String op;

    public String getOp() {
        return op;
    }

    public AspTermOpr(int n){
        super(n);
    }

    static AspTermOpr parse(Scanner s){
        enterParser("Term Opr");

        AspTermOpr ato = new AspTermOpr(s.curLineNum());

        // Sjekker hvilket tegn vi har, og setter pluss eller minus til dette
        // avhengig av hvilket det er
        switch (s.curToken().kind) {
            case plusToken: ato.op = "+";
            skip(s, plusToken); break;
            case minusToken: ato.op = "-";
            skip(s, minusToken); break;
        }

        leaveParser("Term Opr");

        return ato;
    }

    //Se forklaring for prettyPrint() i AspSyntax
    @Override
    void prettyPrint(){
        if(op.equals("+")){
            Main.log.prettyWrite("+ ");
        }
        else{
            Main.log.prettyWrite("- ");
        }
    }

    // IKKE FERDIG
    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }
}
