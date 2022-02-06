package AST;

import Printer.Printer;
import SYMBOL_TABLE.SYMBOL_TABLE;

import java.util.Collections;
import java.util.List;
import TYPES.*;
import IR.*;

public class AST_dec_WHILE extends AST_dec
{
	public AST_EXP cond;
	public List<AST_dec> body;

	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/

	@Override
	public IR_Code PrintCode() {
		AST_GRAPHVIZ.getInstance().logNode(SerialNumber,
				"While\nCond, commands");
		cond.PrintMe();
		AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, cond.getSerialNumber());
		for(AST_dec command: body)
		{
			command.PrintMe();
			AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, command.getSerialNumber());
		}
		return null;
	}

	public AST_dec_WHILE(AST_EXP cond, List<AST_dec> body, int line)
	{
		SerialNumber = AST_Node_Serial_Number.getFresh();
		this.line = line;
		this.cond = cond;
		this.body = body;
	}

	@Override
	public void PrintMe() {
		AST_GRAPHVIZ.getInstance().logNode(SerialNumber,
				"While\nCond, commands");
		cond.PrintMe();
		AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, cond.getSerialNumber());
		for(AST_dec command: body)
		{
			command.PrintMe();
			AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, command.getSerialNumber());
		}
	}


	public TYPE SemantMe()
	{
		/****************************/
		/* [0] Semant the Condition */
		/****************************/
		if (cond.SemantMe() != TYPE_INT.getInstance())
		{
			System.out.format(">> ERROR [%d:%d] condition inside WHILE is not integral\n",2,2);
			Printer.printError(line);
		}

		/*************************/
		/* [1] Begin Class Scope */
		/*************************/
		SYMBOL_TABLE.getInstance().beginScope("while");

		/***************************/
		/* [2] Semant Data Members */
		/***************************/
		Collections.reverse(body);
		for (AST_dec line: body)
		{line.SemantMe();}

		/*****************/
		/* [3] End Scope */
		/*****************/
		SYMBOL_TABLE.getInstance().endScope();

		/*********************************************************/
		/* [4] Return value is irrelevant for class declarations */
		/*********************************************************/
		return null;
	}
}