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

    @Override
    void prettyPrint(){
        int amount_p = list_name.size() - 1;
        Main.log.prettyWrite("def ");
        name.prettyPrint();
        Main.log.prettyWrite("(");
        if(list_name.isEmpty()){
            Main.log.prettyWrite(")");
            Main.log.prettyWrite(":");
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
            Main.log.prettyWrite(")");
            Main.log.prettyWrite(":");
            suite.prettyPrint();
        }

    }


    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 3:
        return null;
    }
}
