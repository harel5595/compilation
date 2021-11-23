   
import java.io.*;
import java.io.PrintWriter;
import java_cup.runtime.Symbol;
import AST.*;

public class Main
{
	static public void main(String argv[])
	{
		Lexer l;
		Parser p = null;
		Symbol s;
		boolean error = false;
		AST_Program AST;
		FileReader file_reader;
		PrintWriter file_writer = null;
		String inputFilename = argv[0];
		String outputFilename = argv[1];
		
		try
		{
			/********************************/
			/* [1] Initialize a file reader */
			/********************************/
			file_reader = new FileReader(inputFilename);

			/********************************/
			/* [2] Initialize a file writer */
			/********************************/
			file_writer = new PrintWriter(outputFilename);
			
			/******************************/
			/* [3] Initialize a new lexer */
			/******************************/
			l = new Lexer(file_reader);
			
			/*******************************/
			/* [4] Initialize a new parser */
			/*******************************/
			p = new Parser(l);

			/***********************************/
			/* [5] 3 ... 2 ... 1 ... Parse !!! */
			/***********************************/
			AST = (AST_Program) p.parse().value;
			
			/*************************/
			/* [6] Print the AST ... */
			/*************************/
			AST.PrintMe();

			file_writer.print("OK");
			
			/*************************/
			/* [7] Close output file */
			/*************************/
			file_writer.close();
			
			/*************************************/
			/* [8] Finalize AST GRAPHIZ DOT file */
			/*************************************/
			AST_GRAPHVIZ.getInstance().finalizeFile();
    	}
			     
		catch (Exception e)
		{
			e.printStackTrace();
			error = true;
		}

		try
		{
			if(error)
			{
				if(file_writer != null)
					file_writer.close();
				file_writer = new PrintWriter(outputFilename);
				if(p != null && p.error_line != -1)
					file_writer.print(String.format("ERROR(%s)",p.error_line));
				else
					file_writer.print("ERROR");
				file_writer.close();
			}
		}
		catch (Error e)
		{
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}


