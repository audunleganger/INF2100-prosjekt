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
           //nothing
          }
          else if (amount > indents[numIndents-1]) {
              indents[numIndents] = amount;
              numIndents = numIndents + 1;
              Token t = new Token(indentToken,curLineNum());
              curLineTokens.add(t);
              Main.log.noteToken(t);
          }

          // Check at jeg har gjort dedent riktig er litt ussikkert på den
          else  {
            for(int i = 0; i < numIndents; i++)   {
              if(indents[i] == amount)  {
                int temp = numIndents - (i + 1);
                for(int g = 0; g < temp; g++){
                  Token t = new Token(dedentToken, curLineNum());
                  curLineTokens.add(t);
                  Main.log.noteToken(t);
                  indents[numIndents - (g + 1)] = 0; // det her må den ikke gjøre, vi kan la elemente være
                }
                numIndents = i + 1;
                break;
              }
              else if (i == (numIndents - 1) && indents[i] != amount)   {
                System.out.println("Dedent Error has occured: no match was found then dedenting in line: " + curLineNum());
                System.exit(0);
              }
            }
          }


          //Work in progress
          int letter_counter = 0;
          mainloop:
          for (int a = 0; a<line.length(); a++){
            if(line.charAt(a) == ('#')) {
              System.out.println("this is a comment"); // ser bort fra at comment kan være lengere i teksten, (vetikke om det kommer til å funke)
              break mainloop;
            }

            if (isLetterAZ(line.charAt(a)) != true)  {
              if(isDigit(line.charAt(a))) {
                if(isLetterAZ(line.charAt(a-1))) {
                  for(int b = a; b<line.length(); b++) {
                      if(!isName(line.charAt(b))) {
                        System.out.println("I should come here");
                        Token temp = new Token(nameToken, curLineNum());
                        temp.name = line.substring(letter_counter, b);
                        curLineTokens.add(temp);
                        Main.log.noteToken(temp);
                        letter_counter = b + 1;
                        a = b;
                        break;
                      }
                    }
                  }
                  else {
                    System.out.println("I should never come here");
                    //this is a number either float or int
                  }
                }
                else if (line.charAt(a) == '\'' ) {
                  System.out.println("Maybe i come here?");
                  letter_counter = a;
                  for(int b = a + 1; b<line.length(); b++) {
                    if(line.charAt(b) == '\'') {
                      Token temp = new Token(stringToken, curLineNum());
                      System.out.println(line.substring(a + 1,b));
                      temp.stringLit = line.substring(a + 1,b);
                      curLineTokens.add(temp);
                      Main.log.noteToken(temp);
                      letter_counter = b + 1;
                      a = b;
                      break;
                    }
                  }
                }
                else if(checkOperator(Character.toString(line.charAt(a - 1)))) {
                  if(checkOperator(line.substring(a, a+1))) {
                    Token temp = new Token(get_Operator(line.substring(a,a+1)), curLineNum());
                    curLineTokens.add(temp);
                    Main.log.noteToken(temp);
                    letter_counter = a + 1;
                    a = a + 1;
                  }
                  else {
                    Token temp = new Token(get_Operator(Character.toString(line.charAt(a))), curLineNum());
                    curLineTokens.add(temp);
                    Main.log.noteToken(temp);
                    letter_counter = a;
                  }
                }
                else {
                  System.out.println("i never come here");
                  Token temp = new Token(nameToken, curLineNum());
                  temp.name = line.substring(letter_counter,a);
                  temp.checkResWords();
                  curLineTokens.add(temp);
                  Main.log.noteToken(temp);
                  letter_counter = a;
                }
              }
                // Ikke en bokstav
                // Ved aa benytte oss av tokenkind, bruk checkResWords for andre occured

                // Sjekk om det passer i checkResWords - hvis ikke er det name token
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
          curLineTokens.add(new Token(newLineToken,curLineNum()));


          //trenger ikke den akkurat naa siden jeg debuger shit
      /*  for (Token t: curLineTokens){
          Main.log.noteToken(t);
        }*/
    }

    public boolean checkOperator(String s){
      for(TokenKind tk : EnumSet.range(asToken,rightParToken)){
        if(s.equals(tk.image)){
          return true;
        }
      }
      return false;
    }

    public TokenKind get_Operator(String s){
      for(TokenKind tk : EnumSet.range(asToken, rightParToken)){
        if(s.equals(tk.image)){
          return tk;
        }
      }
      return null;
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

    private boolean isName(char c){
      return ('A'<=c && c<='Z') || ('a'<=c && c<='z') || (c=='_' || ('0' <=c && c<='9'));
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
