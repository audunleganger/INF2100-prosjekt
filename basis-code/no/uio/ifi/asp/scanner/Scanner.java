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
            // sjekker om vi har noen dedent å gjøre før vi avslutter programmet
            if (line == null) {
                if(numIndents > 1){
                    for(int i = numIndents; i != 1; i--)   {
                          Token t = new Token(dedentToken, curLineNum());
                          curLineTokens.add(t);
                          Main.log.noteToken(t);
                    }
                }
                sourceFile.close();
                sourceFile = null;
                Token temp = new Token(eofToken, curLineNum());
                curLineTokens.add(temp);
                Main.log.noteToken(temp);
                return;
            } else {
                Main.log.noteSourceLine(curLineNum(), line);
            }
        } catch (IOException e) {
            sourceFile = null;
            scannerError("Unspecified I/O error!");
        }


          // Konverterer tabs til whitespaces, lagrer det i variabel amount
          String withouttab = expandLeadingTabs(line);
          int amount = findIndent(withouttab);
          // Sjekker om antall indenteringer mathcer linjen over
          if(amount == indents[numIndents-1]){
           // Riktig antall indents, gjoer ingenting
          }
          else if (amount > indents[numIndents-1]) {
              indents[numIndents] = amount;
              numIndents = numIndents + 1;
              Token t = new Token(indentToken,curLineNum());
              curLineTokens.add(t);
          }


          else  {
            for(int i = 0; i < numIndents; i++)   {
              if(indents[i] == amount)  {
                int temp = numIndents - (i + 1);
                for(int g = 0; g < temp; g++){
                  Token t = new Token(dedentToken, curLineNum());
                  curLineTokens.add(t);
                  indents[numIndents - (g + 1)] = 0; // Unoedvendig siden det blir overskrevet ved neste indent
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

          if(line.trim().isEmpty()){
              for (Token t: curLineTokens){
                  Main.log.noteToken(t);
              }
            System.out.println("This line is empty, linenumber: " + curLineNum());
            return;
          }


          int letter_counter = 0;
          String word;
          mainloop:
          // I hovedloopen vil vi gaa gjennom linjen og lagre indeksen i variabel a. Hver gang vi finner noe
          // stopper a, og vi itererer med en annen varabel (letter_counter), og til slutt lagrer vi det vi leste
          // som en ny token (hvis det som staar er gyldig)
          // Vi gaar gjennom linjen, og sjekker i foerste omgang om det er en kommentar (#)
          for (int a = 0; a<line.length(); a++){
            if(line.charAt(a) == ('#')) {
              System.out.println("this is a comment"); // ser bort fra at comment kan være lengere i teksten, (vetikke om det kommer til å funke)
              return;
            }
            // Vi sjekker om det vi har er et tall
              if(isDigit(line.charAt(a))){
                letter_counter = a;
                // Vi fortsetter aa iterer gjennom helt til vi finner noe som IKKE er et tall

                while(letter_counter+1 != line.length() && isDigit(line.charAt(letter_counter+1))){
                  letter_counter++;
                }
                // Hvis vi finner et punktum ".", vet vi at vi jobber med en float
                if(letter_counter + 1 !=  line.length() && line.charAt(letter_counter + 1) == '.'){
                  letter_counter++;
                  // Finner alle desimalene i floaten
                  while(letter_counter+1 != line.length() && isDigit(line.charAt(letter_counter+1))){
                    letter_counter++;
                  }
                  // Vi sjekker at neste tegn ikke er en bokstav eller punktum (ugyldig statement)
                  if(isLetterAZ(line.charAt(letter_counter)) || line.charAt(letter_counter) == '.'){
                    System.out.println("Illegal statement, expecting float at line: " + curLineNum() + ", got:");
                    System.out.println(line.substring(a,letter_counter+1) + "...");
                    System.exit(0);
                  }
                  // Hvis dette ikke er sant, vet vi at vi har en operator eller et whitespace, noe som kan vaere gyldig,
                  // og vi kan bruke find_float til aa finne float-verdien
                  find_float(line.substring(a,letter_counter+1));
                }
                else{
                  // Dette er et heltall, og vi sjekker igjen om det neste tegnet er en bokstav
                  // Hvis det er det, er uttrykket ugyldig

                  if(isLetterAZ(line.charAt(letter_counter))){
                    System.out.println("Illegal statement, expecting Integer at line: " + curLineNum() + ", got:");
                    System.out.println(line.substring(a,letter_counter+1) + "...");
                    System.exit(0);
                  }
                  // Uttrykket er gyldig, og vi har et heltall(int)
                  find_integer(line.substring(a,letter_counter+1));
                }
                // Vi shifter a over til letter_counter, slik at vi fortsetter etter at tokenen er ferdig
                a = letter_counter;
              }
              // Ikke float eller heltall, sjekker om det er et ord
              else if(isLetterAZ(line.charAt(a))){
                letter_counter = a;

                // Det er et ord, saa vi begynner aa lese til vi finner noe annet enn bokstaver
                while(letter_counter + 1 != line.length() && isName(line.charAt(letter_counter + 1))){
                  letter_counter++;
                }

                // Naa har vi et fullt ord, saa vi sjekker om det er et ord med tall eller om det er en key
                find_name_or_key(line.substring(a,letter_counter+1));
                // Vi shifter a over til letter_counter, slik at vi faar med hele ordet eller key-valuen
                a = letter_counter;
                }
                // Vi sjekker om det er en operator via checkOperator-funksjonen
                else if(checkOperator(line.substring(a,a+1))){
                  // Vi vet at det er en operator, maa sjekke om det er en to-tegns-operator (f.eks "==" eller ">=")
                  letter_counter = a;
                  if( a+1 != line.length() && checkOperator(line.substring(letter_counter, letter_counter+2))){
                    // Vi vet det er en to-tegns-operator, saa vi oppretter en token og pusher den paa curLineTokens
                    Token temp = new Token(get_Operator(line.substring(letter_counter, letter_counter+2)), curLineNum());
                    curLineTokens.add(temp);
                    letter_counter++;
                  }
                  else{
                      // Vi vet det er en ett-tegns-operator, saa vi oppretter en token og pusher den paa curLineTokens
                    Token temp = new Token(get_Operator(line.substring(letter_counter, letter_counter+1)), curLineNum());
                    // Vi pusher paa stacken
                    curLineTokens.add(temp);
                  }
                  a = letter_counter;
                }
                // Vi sjekker om det en string (begynner med " eller ')
                else if(line.charAt(a) == '\'' || line.charAt(a) == '\"'){
                  letter_counter = a;
                  // Vi oeker letter_counter, saann at vi ikke faar med " eller ' i selve stringen
                  letter_counter++;

                  // Vi itererer til vi finner slutten av stringen
                  while(line.charAt(letter_counter) != '\'' && line.charAt(letter_counter) != '\"'){
                    if(letter_counter + 1 == line.length() && (line.charAt(letter_counter) != '\'' || line.charAt(letter_counter) != '\"')){
                        System.out.println("String error, was expecting \' or \", but didnt find it, at line: " + curLineNum());
                        System.exit(0);
                    }
                    letter_counter++;
                  }
                  // Vi fant et ord, lager string-token og pusher paa stacken
                  Token temp = new Token (stringToken, curLineNum());
                  temp.stringLit = line.substring(a + 1, letter_counter);
                  curLineTokens.add(temp);
                  // Shifter a over til letter_counter
                  a = letter_counter;
                }
                // Hvis vi kom hit, er det et whitespace. Vi gjoer ingenting
            }
          curLineTokens.add(new Token(newLineToken,curLineNum()));


          //trenger ikke den akkurat naa siden jeg debuger shit
          for (Token t: curLineTokens){
              Main.log.noteToken(t);
          }
    }

    private void find_integer(String word){
      long value = Long.parseLong(word);
      Token temp = new Token(integerToken, curLineNum());
      temp.integerLit = value;
      curLineTokens.add(temp);
    }

    private void find_name_or_key(String word){
      Token temp = new Token(nameToken, curLineNum());
      temp.name = word;
      temp.checkResWords();
      curLineTokens.add(temp);
    }

    private void find_float(String word){
      if(!word.contains("[a-zA-Z]+")){
        double number = Float.parseFloat(word);
        Token temp = new Token(floatToken, curLineNum());
        temp.floatLit = number;
        curLineTokens.add(temp);;
      }
      else{
        System.out.println("Float constraction error, not a float, in line: " + curLineNum());
        System.exit(0);
      }
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
      return ('A'<=c && c<='Z') || ('a'<=c && c<='z') || (c=='_') || ('0' <=c && c<='9');
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
