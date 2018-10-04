package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.*;
import no.uio.ifi.asp.main.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;


class AspWhileStmt extends AspStmt{
    AspExpr test;
    AspSuite body;

    AspWhileStmt(int n) {
        super(n);
    }

    static AspWhileStmt parse(Scanner s) {
        enterParser("while stmt");
        AspWhileStmt aws = new AspWhileStmt(s.curLineNum());
        skip(s, whileToken);
        aws.test = AspExpr.parse(s);
        skip(s, colonToken);
        aws.body = AspSuite.parse(s);
        leaveParser("while stmt");
        return aws;
    }

    @Override
    void prettyPrint() {
        Main.log.prettyWrite(" while ");
        test.prettyPrint();
        Main.log.prettyWrite(" : ");
        body.log.prettyPrint();
    }

}
