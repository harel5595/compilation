import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

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

    private boolean is_end_of_token(char to_check)
    {
        if(to_check == '\n' || to_check == ' ' || to_check == ')' ||
                to_check == '(' || to_check == '{' || to_check == '}'
                || to_check == '[' || to_check == ']' || to_check == '+'
                || to_check == '+' || to_check == '-' || to_check == '/'
                || to_check == '*')
        {

        }
    }

    private Symbol check_for_saved_words(String token)
    {
        if(Objects.equals(token, "class"))
        {
            return new Symbol(TokenNames.CLASS);
        }
        else if(Objects.equals(token, "nil"))
        {
            return new Symbol(TokenNames.NIL);
        }
        else if(Objects.equals(token, "array"))
        {
            return new Symbol(TokenNames.ARRAY);
        }
        else if(Objects.equals(token, "while"))
        {
            return new Symbol(TokenNames.WHILE);
        }
        else if(Objects.equals(token, "int"))
        {
            return new Symbol(TokenNames.NUMBER);
        }
        else if(Objects.equals(token, "extends"))
        {
            return new Symbol(TokenNames.EXTENDS);
        }
        else if(Objects.equals(token, "return"))
        {
            return new Symbol(TokenNames.RETURN);
        }
        else if(Objects.equals(token, "new"))
        {
            return new Symbol(TokenNames.NEW);
        }
        else if(Objects.equals(token, "if"))
        {
            return new Symbol(TokenNames.IF);
        }
        else if(Objects.equals(token, "string"))
        {
            return new Symbol(TokenNames.STRING);
        }
        else
        {
            return new Symbol(TokenNames.ID, token);
        }
    }

    private Symbol next_string_token(char first_char) throws IOException {
        String token = String.valueOf(first_char);
        char[] buff = new char[1];
        int result;
        result = fileReader.read(buff, offset, 1);
        while (result > 0 && ((buff[0] >= 'a' && buff[0] <= 'z') || (buff[0] >= '0' && buff[0] <= '9') || (buff[0] >= 'A' && buff[0] <= 'Z')))
        {
            token = token.concat(String.valueOf(buff[0]));
        }

        return check_for_saved_words(token);
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
        else if(buff[0] == ';')
        {
            return new Symbol(TokenNames.SEMICOLON);
        }
        else if(buff[0] <= '9' && buff[0] >= '0' )
        {
            return new Symbol(TokenNames.NUMBER);
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
