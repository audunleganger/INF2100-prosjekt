package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.*;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.runtime.*;



class AspFuncDef extends AspStmt{
    AspName name;
    ArrayList<AspName> list_name = new ArrayList<>();
    AspSuite suite;

    AspFuncDef(int n){
        super(n);
    }

    // Leser inn navnet, og alle potensielle argumenter via AspName
    // sin parse-metode, og lagrer dette i variablene name (funksjonsnavn)
    // og list_name (argumenter). Parser deretter den foelgende kodeblokken
    // med AspSuite sin parse-metode
    static AspFuncDef parse(Scanner s){
        enterParser("Func Def");

        AspFuncDef afd = new AspFuncDef(s.curLineNum());

        skip(s, defToken);
        afd.name = AspName.parse(s);
        skip(s, leftParToken);

        while(true){
            if(s.curToken().kind == rightParToken){
                break;
            }
            afd.list_name.add(AspName.parse(s));
            if(s.curToken().kind != commaToken){
                break;
            }
            skip(s, commaToken);
        }
        skip(s, rightParToken);
        skip(s, colonToken);
        afd.suite = AspSuite.parse(s);

        leaveParser("Func Def");

        return afd;

    }

    //Se forklaring for prettyPrint() i AspSyntax
    @Override
    void prettyPrint(){
        int amount_p = list_name.size() - 1;
        Main.log.prettyWrite("def ");
        name.prettyPrint();
        Main.log.prettyWrite("( ");
        if(list_name.isEmpty()){
            Main.log.prettyWrite(") ");
            Main.log.prettyWrite(": ");
            suite.prettyPrint();
        }
        else{
            for(AspName an : list_name){
                an.prettyPrint();
                if(amount_p != 0){
                    Main.log.prettyWrite(", ");
                    amount_p--;
                }
            }
            Main.log.prettyWrite(") ");
            Main.log.prettyWrite(": ");
            suite.prettyPrint();
        }

    }


    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        String aName = name.word;
        ArrayList<String> aNames = new ArrayList<>();

        for (AspName an : list_name) {
            aNames.add(an.word);
        }
        curScope.assign(aName, new RuntimeFunc(suite, aNames, curScope));

        return null;
    }
}
