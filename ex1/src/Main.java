   
import java.io.*;
import java.io.PrintWriter;

import java_cup.runtime.Symbol;
   
public class Main
{
	static public void main(String argv[])
	{
		Lexer l;
		Symbol s;
		FileReader file_reader;
		PrintWriter file_writer = null;
		String inputFilename = argv[0];
		String outputFilename = argv[1];
		boolean error = false;
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

			/***********************/
			/* [4] Read next token */
			/***********************/
			s = l.next_token();

			/********************************/
			/* [5] Main reading tokens loop */
			/********************************/
			while (s.sym != TokenNames.EOF)
			{
				/************************/
				/* [6] Print to console */
				/************************/
				System.out.print(TokenNames.to_name(s.sym));
				if(s.sym == TokenNames.ID || s.sym == TokenNames.NUMBER || s.sym == TokenNames.STRING)
				{
					System.out.print("(");
					System.out.print(s.value);
					System.out.print(")");
				}

				System.out.print("[");
				System.out.print(l.getLine());
				System.out.print(",");
				System.out.print(l.getTokenStartPosition());
				System.out.print("]");
				//System.out.print(s.value);
				System.out.print("\n");
				
				/*********************/
				/* [7] Print to file */
				/*********************/
				file_writer.print(TokenNames.to_name(s.sym));
				if(s.sym == TokenNames.ID || s.sym == TokenNames.NUMBER || s.sym == TokenNames.STRING)
				{
					file_writer.print("(");
					file_writer.print(s.value);
					file_writer.print(")");
				}

				file_writer.print("[");
				file_writer.print(l.getLine());
				file_writer.print(",");
				file_writer.print(l.getTokenStartPosition());
				file_writer.print("]");
				//file_writer.print(s.value);
				file_writer.print("\n");

				//file_writer.print(l.getLine());
				//file_writer.print(": ");
				//file_writer.print(s.value);
				//file_writer.print("\n");
				
				/***********************/
				/* [8] Read next token */
				/***********************/
				s = l.next_token();
			}
			
			/******************************/
			/* [9] Close lexer input file */
			/******************************/
			l.yyclose();

			/**************************/
			/* [10] Close output file */
			/**************************/
			file_writer.close();
    	}
			     
		catch (Exception | Error e)
		{
			error = true;
		}

		try
		{
			if(error)
			{
				if(file_writer != null)
					file_writer.close();
				file_writer = new PrintWriter(outputFilename);
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


