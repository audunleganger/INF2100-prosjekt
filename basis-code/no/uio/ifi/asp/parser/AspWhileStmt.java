package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.*;
import no.uio.ifi.asp.main.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.runtime.*;


class AspWhileStmt extends AspStmt{
    AspExpr expr;
    AspSuite suite;

    AspWhileStmt(int n) {
        super(n);
    }

    // Leser uttrykket for at while-loopen skal kjoere, og parser det via AspExpr
    // sin parse-metode. Vil deretter parse foelgende kodeblokk via AspSuite sin
    // parse-metode
    static AspWhileStmt parse(Scanner s) {
        enterParser("while stmt");

        AspWhileStmt aws = new AspWhileStmt(s.curLineNum());

        skip(s, whileToken);
        aws.expr = AspExpr.parse(s);
        skip(s, colonToken);
        aws.suite = AspSuite.parse(s);

        leaveParser("while stmt");

        return aws;
    }

    //Se forklaring for prettyPrint() i AspSyntax
    @Override
    void prettyPrint() {
        Main.log.prettyWrite("While ");
        expr.prettyPrint();
        Main.log.prettyWrite(": ");
        suite.prettyPrint();
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 3:
        /*
        RuntimeValue v = expr.eval(curScope);
        v = suite.eval(curScope);
        return v;
        */
        return null;
    }

}
