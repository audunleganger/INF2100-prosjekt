package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.*;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.runtime.*;



class AspIfStmt extends AspStmt{
    ArrayList<AspExpr> expr = new ArrayList<>();
    ArrayList<AspSuite> suite = new ArrayList<>();

    AspIfStmt(int n)    {
        super(n);
    }

    static AspIfStmt parse(Scanner s)   {
        enterParser("IfStmt");

        AspIfStmt ais = new AspIfStmt(s.curToken());

        skip(s, ifToken);

        while (true)    {
            ais.expr.add(AspExpr.parse(s));
            skip(s, colonToken);
            ais.suite.add(AspSuite.parse(s));
            if (s.curToken().kind != elifToken) {
                break;
            }
            skip(s, elifToken);
        }
        if (s.curToken().kind == elseToken) {
            skip(s, elseToken);
            skip(s, colonToken);

            ais.suite.add(AspSuite.parse(s));

            leaveParser("IfStmt");

            return ais;
        }
    }

    @Override
    void prettyPrint(){
        boolean elsePresent = false;

        if (expr.size() == (suite.size()-1))    {
            elsePresent = true;
        }



        Main.log.prettyWrite(" if ");
        if (expr.size() == 1)   {
            expr.get(0).prettyPrint();
            Main.log.prettyWrite(":\n");
            suite.get(0).prettyPrint();
        }

        else {
            if (elsePresent)    {
                for (int i = 0; i < (suite.size()-1); i++)  {
                    Main.log.prettyWrite(" elif ");
                    expr.get(i).prettyPrint();
                    Main.log.prettyWrite(":\n");
                    suite.get(i).prettyPrint();
                }
                Main.log.prettyWrite(" else ");
                suite.get(suite.size()-1).prettyPrint();

            }
            else    {
                for (int i = 0; i < suite.size(); i++)  {
                    Main.log.prettyWrite(" elif ");
                    expr.get(i).prettyPrint();
                    Main.log.prettyWrite(":\n");
                    suite.get(i).prettyPrint();
                }
            }
        }
    }


    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 3:
        return null;
    }
}
