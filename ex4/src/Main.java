   
import java.io.*;
import java.io.PrintWriter;

import IR.IR_Code;
import MIPS.MIPSGenerator;
import java_cup.runtime.Symbol;
import AST.*;
import TYPES.*;
import Printer.*;

public class Main
{

	static public void main(String argv[])
	{
		Lexer l;
		Parser p = null;
		Symbol s;
		AST_Program AST;
		FileReader file_reader;
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
			Printer.file_writer = new PrintWriter(outputFilename);
			
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
			//AST.PrintMe();

			/**************************/
			/* [7] Semant the AST ... */
			/**************************/
			AST.SemantMe();

			AST.PrintCode();

			/***********************/
			/* [9] MIPS the IR ... */
			/***********************/
			IR_Code.getInstance().MIPSme();

			//Printer.file_writer.print("OK");
			MIPSGenerator.getInstance().finalizeFile();
			/*************************/
			/* [8] Close output file */
			/*************************/
			Printer.file_writer.close();

			/*************************************/
			/* [9] Finalize AST GRAPHIZ DOT file */
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
				if(Printer.file_writer != null)
					Printer.file_writer.close();
				Printer.file_writer = new PrintWriter(outputFilename);
				if(p != null && p.error_line != -1)
					Printer.file_writer.print(String.format("ERROR(%s)",p.error_line));
				else
					Printer.file_writer.print("ERROR");
				Printer.file_writer.close();
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


