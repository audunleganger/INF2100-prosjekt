package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.*;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.runtime.*;



class AspSuite extends AspSyntax{
    ArrayList<AspStmt> stmt = new ArrayList<>();

    AspSuite(int n){
        super(n);
    }

    static AspSuite parse(Scanner s){
        enterParser("Suite");

        AspSuite as = new AspSuite(s.curLineNum());

        skip(s, newLineToken);
        skip(s, indentToken);

        while(true){
            as.stmt.add(AspStmt.parse(s));
            if(s.curToken().kind == dedentToken){
                break;
            }
        }

        skip(s, dedentToken);

        leaveParser("Suite");

        return as;

    }

    @Override
    void prettyPrint(){
        Main.log.prettyWriteln("\n");
        Main.log.prettyIndent();
        for(AspStmt as : stmt){
            as.prettyPrint();
        }
        Main.log.prettyDedent();
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 3:
        return null;
    }
}
