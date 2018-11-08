package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.*;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.runtime.*;



class AspTerm extends AspSyntax{
    ArrayList<AspFactor> factor = new ArrayList<>();
    ArrayList<AspTermOpr> tempo = new ArrayList<>();

    AspTerm(int n){
        super(n);
    }

    // Parser en faktor via AspFactor sin parse-metode, og sjekker deretter om s
    // er en term-operator. Er det det, vil dette parses via AspTermOpr sin parse-metode.
    // Dette vil den fortsette med helt til den mottar noe som ikker en term-operator
    static AspTerm parse(Scanner s){
        enterParser("Term");

        AspTerm at = new AspTerm(s.curLineNum());

        while(true){
            at.factor.add(AspFactor.parse(s));

            if(s.isTermOpr()){
                at.tempo.add(AspTermOpr.parse(s));
            }
            else{
                break;
            }
        }
        leaveParser("Term");

        return at;
    }

    //Se forklaring for prettyPrint() i AspSyntax
    @Override
    void prettyPrint(){
        int teller = 0;
        if(factor.size() == 1) {
            factor.get(0).prettyPrint();
        }
        else{
            for(AspFactor af : factor){
                if (teller == tempo.size()) {
                    af.prettyPrint();
                    break;
                }
                af.prettyPrint();
                tempo.get(teller).prettyPrint();
                teller++;
            }
        }
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        RuntimeValue v = factor.get(0).eval(curScope);
        for (int i = 1; i < factor.size(); i++){
            String k = tempo.get(i-1).getOp();
            switch (k){
                case "-":
                    v = v.evalSubtract(factor.get(i).eval(curScope), this); break;
                case "+":
                    v = v.evalAdd(factor.get(i).eval(curScope), this); break;
                default:
                    Main.panic("Illegal term operator: " + k + "!");
            }
        }
        return v;
    }

}
