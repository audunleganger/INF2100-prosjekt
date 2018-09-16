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


          int letter_counter = 0;
          String word;
          mainloop:
          // seems like a finish product, test as much as u want if something doesnt work plz contact me
          for (int a = 0; a<line.length(); a++){
            if(line.charAt(a) == ('#')) {
              System.out.println("this is a comment"); // ser bort fra at comment kan være lengere i teksten, (vetikke om det kommer til å funke)
              break mainloop;
            }
// we found that we had letters and something else now, now we need find out what is that new letter and what was before it
            // check if this is a integer or float
              if(isDigit(line.charAt(a))){
                letter_counter = a;
                //going through it, until we find something that is not a integer

                while(letter_counter+1 != line.length() && isDigit(line.charAt(letter_counter+1))){
                  letter_counter++;
                }
                //this is a float

                if(line.charAt(letter_counter) == '.'){
                  letter_counter++;
                  //finding the rest of float
                  while(isDigit(line.charAt(letter_counter+1))){
                    letter_counter++;
                  }
                  //before sending we need to check that we the next char is not letter
                  if(isLetterAZ(line.charAt(letter_counter)) || line.charAt(letter_counter) == '.'){
                    System.out.println("Illegal statement, expecting float at line: " + curLineNum() + ", got:");
                    System.out.println(line.substring(a,letter_counter+1) + "...");
                    System.exit(0);
                  }
                  //if this is not true then we got a white space, operation, or string or something that maybe is allowed
                  // and we can send to find_float to get us the float value
                  find_float(line.substring(a,letter_counter+1));
                }
                else{
                  // this integer againg we need to check if next char is not a letter
                  // throw a error msg and get on end the program

                  if(isLetterAZ(line.charAt(letter_counter))){
                    System.out.println("Illegal statement, expecting Integer at line: " + curLineNum() + ", got:");
                    System.out.println(line.substring(a,letter_counter+1) + "...");
                    System.exit(0);
                  }
                  // since everything is okey we go on to and send the word to
                  find_integer(line.substring(a,letter_counter+1));
                }
                // and lastly we want to that the a to start from letter_counter possition
                // but neeed to take - 1 from counter since a will increase with 1
                a = letter_counter;
              }
              // it wasnt a integer so, we will check if it is a word
              else if(isLetterAZ(line.charAt(a))){
                //since it is a letter now we can use isname, since we can a key token or a name token
                letter_counter = a;
                
                while(letter_counter + 1 != line.length() && isName(line.charAt(letter_counter + 1))){
                  letter_counter++;
                }
                // now we found the full word, need to check if it is name with numbers or key or name without numbers
                // but all we need do is to send this to function down to do all the job for us
                find_name_or_key(line.substring(a,letter_counter+1));
                // it will either be a name or ke token
                // also we want that a will start from letter_counter possition, again we need to take - 1 sine a will increase with 1
                a = letter_counter;
                }
                // since this was not a letter or a integer, lets check if it is a operator, since
                // the fuc takes string, we need to do substring
                else if(checkOperator(line.substring(a,a+1))){
                  //it was operator, now we got to check if the operator is something like this: ==
                  // we allways sett letter_counter to be a, se we know how many extra letters we move from a
                  // need to check if this is the end of the line, if it is then there is only one operator
                  letter_counter = a;
                  if( a+1 != line.length() && checkOperator(line.substring(letter_counter, letter_counter+2))){
                    //if this true then we got a something like this ==
                    // we can use the function to find what kind of token it is:
                    Token temp = new Token(get_Operator(line.substring(letter_counter, letter_counter+2)), curLineNum());
                    // now just to add it to the list
                    curLineTokens.add(temp);
                    Main.log.noteToken(temp);
                    // we add 1 to letter_counter since we moved with 1
                    letter_counter++;
                  }
                  else{
                    // this is simple operator, so we will just add it and move on
                    // again using the same fuc we find the right operator token
                    Token temp = new Token(get_Operator(line.substring(letter_counter, letter_counter+1)), curLineNum());
                    // now just to add it to the list
                    curLineTokens.add(temp);
                    Main.log.noteToken(temp);
                    // and that is it
                  }
                  //we put a to be at the right possition, we could have avoided the use of letter_counter
                  // but since it is important in other if/else we using it here to
                  a = letter_counter;
                }
                //lastly we need to check if this a string
                // the string can start with "" or with ''
                else if(line.charAt(a) == '\'' || line.charAt(a) == '\"'){
                  letter_counter = a;
                  //we increas letter_counter since we dont want to start at the same spot, unlike other places
                  letter_counter++;

                  // we need to find the end of the string,
                  while(line.charAt(letter_counter) != '\'' && line.charAt(letter_counter) != '\"'){
                    letter_counter++;
                  }
                  // now that we found the word we need to add it to the token
                  Token temp = new Token (stringToken, curLineNum());
                  // we need to do pluss 1 since we dont want to take ' with us
                  temp.stringLit = line.substring(a + 1, letter_counter);
                  curLineTokens.add(temp);
                  Main.log.noteToken(temp);
                  // now we need to set a to the place we want it to be to
                  a = letter_counter;
                }
                // if we came here it means a is white spaace
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

    private void find_integer(String word){
        System.out.println(word);
      long value = Long.parseLong(word);
      Token temp = new Token(integerToken, curLineNum());
      temp.integerLit = value;
      curLineTokens.add(temp);
      Main.log.noteToken(temp);
    }

    private void find_name_or_key(String word){
      Token temp = new Token(nameToken, curLineNum());
      temp.name = word;
      temp.checkResWords();
      curLineTokens.add(temp);
      Main.log.noteToken(temp);
    }

    private void find_float(String word){
      if(!word.contains("[a-zA-Z]+")){
        double number = Float.parseFloat(word);
        Token temp = new Token(floatToken, curLineNum());
        temp.floatLit = number;
        curLineTokens.add(temp);
        Main.log.noteToken(temp);
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
