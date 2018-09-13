package no.uio.ifi.asp.scanner;

import java.io.*;
import java.util.*;

import no.uio.ifi.asp.main.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class Scanner {
    private LineNumberReader sourceFile = null;
    private String curFileName;
    private ArrayList<Token> curLineTokens = new ArrayList<>();
    private int indents[] = new int[100];
    private int numIndents = 0;
    private final int tabDist = 4;


    public Scanner(String fileName) {
        curFileName = fileName;
        indents[0] = 0;  numIndents = 1;

        try {
            sourceFile = new LineNumberReader(
            new InputStreamReader(
            new FileInputStream(fileName),
            "UTF-8"));
        } catch (IOException e) {
            scannerError("Cannot read " + fileName + "!");
        }
    }


    private void scannerError(String message) {
        String m = "Asp scanner error";
        if (curLineNum() > 0)
        m += " on line " + curLineNum();
        m += ": " + message;

        Main.error(m);
    }


    public Token curToken() {
        while (curLineTokens.isEmpty()) {
            readNextLine();
        }
        return curLineTokens.get(0);
    }


    public void readNextToken() {
        if (! curLineTokens.isEmpty())
        curLineTokens.remove(0);
    }


    public boolean anyEqualToken() {
        for (Token t: curLineTokens) {
            if (t.kind == equalToken) return true;
        }
        return false;
    }


    private void readNextLine() {
        curLineTokens.clear();

        // Read the next line:
        String line = null;
        try {
            line = sourceFile.readLine();
            if (line == null) {
                sourceFile.close();
                sourceFile = null;
            } else {
                Main.log.noteSourceLine(curLineNum(), line);
            }
        } catch (IOException e) {
            sourceFile = null;
            scannerError("Unspecified I/O error!");
        }


        // Begynnelse paa oppgaven
          if(line.trim().isEmpty()){
            System.out.println("This line is empty, linenumber: " + curLineNum());
            return;
          }

          // Konverterer tabs til whitespaces, lagrer det i variabel amount
          String withouttab = expandLeadingTabs(line);
          int amount = findIndent(withouttab);

          // Sjekker om antall indenteringer mathcer linjen over
          if(amount == indents[numIndents-1]){
            System.out.println("Dont neeed to indent");
          }
          else if (amount > indents[numIndents-1]) {
              System.out.println("Need to indent");
          }
          else{
            System.out.println("Need to find amount of dedent we need to do");
          }

          // Deler linjen opp i string-array, separert med whitespaces
          // Vil alle tokens vaere separert med whitespaces? (3 + 5 er, 3+5 er ikke)
          String[] tekst = line.split(" ", 200);

          // Leser hver enkelt ws-separert ord i filen, sjekker om det er kommentar (#)
          for(int a = 0; a<tekst.length; a++){
            if(tekst[a].contains("#")){
              System.out.println("this a comment, we dont neeed to take it at: " +  curLineNum());
              break;
            }
            System.out.println("finding what to do with word: " + tekst[a]);

            // Work in progress
            if(tekst[a].contains(TokenKind.equalToken())){
              System.out.println("this word has = token");
            }

          }



          //-- Must be changed in part 1:
          /*
              Oppskrift:
              - Sjekk om linjen er tomt OK
              - Sjekk om linjen inneholder en kommentar
              - Omform TAB-er til blanke (whitespaces)
              - Tell antall blanke (whitespaces): n
              - Hvis n > Indents.top
                  - Push n på indents
                  - Legg til INDENT-token på curLineTokens
              - Hvis n < Indents.top
                  - Pop Indents.top
                  - Legg til en DEDENT-token på curLineTokens
              - Hvis n != Indents.top, har vi indenteringsfeil
              - På slutten av siste linje: For alle indents
                som er > 0, legg til DEDENT-token i curLineTokens
          */
          // Terminate line:
          //curLineTokens.add(new Token(newLineToken,curLineNum()));



        for (Token t: curLineTokens){
          Main.log.noteToken(t);
        }
    }

    public int curLineNum() {
        return sourceFile!=null ? sourceFile.getLineNumber() : 0;
    }

    private int findIndent(String s) {
        int indent = 0;

        while (indent<s.length() && s.charAt(indent)==' ') indent++;
        return indent;
    }

    private String expandLeadingTabs(String s) {
        String newS = "";
        for (int i = 0;  i < s.length();  i++) {
            char c = s.charAt(i);
            if (c == '\t') {
                do {
                    newS += " ";
                } while (newS.length()%tabDist != 0);
            } else if (c == ' ') {
                newS += " ";
            } else {
                newS += s.substring(i);
                break;
            }
        }
        return newS;
    }


    private boolean isLetterAZ(char c) {
        return ('A'<=c && c<='Z') || ('a'<=c && c<='z') || (c=='_');
    }


    private boolean isDigit(char c) {
        return '0'<=c && c<='9';
    }


    public boolean isCompOpr() {
        TokenKind k = curToken().kind;
        //-- Must be changed in part 2:
        return false;
    }


    public boolean isFactorPrefix() {
        TokenKind k = curToken().kind;
        //-- Must be changed in part 2:
        return false;
    }


    public boolean isFactorOpr() {
        TokenKind k = curToken().kind;
        //-- Must be changed in part 2:
        return false;
    }


    public boolean isTermOpr() {
        TokenKind k = curToken().kind;
        //-- Must be changed in part 2:
        return false;
    }
}
