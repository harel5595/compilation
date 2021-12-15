package Printer;
import java.io.PrintWriter;

public class Printer {
    static public PrintWriter file_writer = null;
    static public void printError(int line)
    {
        if(file_writer == null)
            System.out.println("fuck");
        file_writer.format("ERROR(%d)", line);
        System.exit(0);
    }
}
