import java.io.FileReader;
import java.io.IOException;

import java_cup.runtime.Symbol;

public class Lexer {
    private final FileReader fileReader;
    private int lineCounter;
    private int offset;
    private int offset_from_start_of_file;
    public Lexer(FileReader file_reader) {
        fileReader = file_reader;
        lineCounter = 0;
        offset = 0;
    }

    public Symbol next_token() throws IOException {
        char[] buff = new char[1];
        String string = "";
        int result;
        result = fileReader.read(buff, offset, 1);
        if(result == -1)
            return new Symbol(TokenNames.EOF);
        else if(buff[0] == '(')
        {

            return new Symbol(TokenNames.LPAREN);
        }
        else if(buff[0] == ')')
        {
            return new Symbol(TokenNames.RPAREN);
        }
        else if(buff[0] == '[')
        {
            return new Symbol(TokenNames.LBRACK);
        }
        else if(buff[0] == ']')
        {
            return new Symbol(TokenNames.RBRACK);
        }
        else if(buff[0] == '}')
        {
            return new Symbol(TokenNames.RBRACE);
        }
        else if(buff[0] == '{')
        {
            return new Symbol(TokenNames.LBRACE);
        }
        else if(buff[0] == '+')
        {
            return new Symbol(TokenNames.PLUS);
        }
        else if(buff[0] == '-')
        {
            return new Symbol(TokenNames.MINUS);
        }
        else if(buff[0] == '/')
        {
            return new Symbol(TokenNames.DIVIDE);
        }
        else if(buff[0] == '*')
        {
            return new Symbol(TokenNames.TIMES);
        }
        else if(buff[0] == '>')
        {
            return new Symbol(TokenNames.GT);
        }
        else if(buff[0] == '<')
        {
            return new Symbol(TokenNames.LT);
        }
        else if(buff[0] == '=')
        {
            return new Symbol(TokenNames.EQ);
        }
        else if(buff[0] == ',')
        {
            return new Symbol(TokenNames.COMMA);
        }
        else if(buff[0] == '.')
        {
            return new Symbol(TokenNames.DOT);
        }
        else if(buff[0] == ':')
        {
            return new Symbol(TokenNames.SEMICOLON);
        }


        return null;
    }

    public int getLine() {
        return lineCounter;
    }

    public int getTokenStartPosition() {
        return offset_from_start_of_file;
    }

    public void yyclose() {
        System.out.println("done");
    }
}
