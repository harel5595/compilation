import java.io.FileReader;
import java_cup.runtime.Symbol;

public class Lexer {
    public Lexer(FileReader file_reader) {
    }

    public Symbol next_token()
    {
        return new Symbol(0);
    }

    public boolean getLine() {
        return false;
    }

    public boolean getTokenStartPosition() {
        return false;
    }

    public void yyclose() {
        System.out.println("done");
    }
}
