package AST;

import java.util.List;

import Printer.Printer;
import SYMBOL_TABLE.SYMBOL_TABLE;
import TYPES.*;
import IR.*;
import TEMP.*;

public class AST_dec_IF extends AST_dec
{
	public AST_EXP cond;
	public List<AST_dec> body;

	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/

	@Override
	public TEMP PrintCode() {

	TEMP condT = null;
	TEMP bodyT = null;

	if(cond != null)
	{
		condT = cond.PrintCode();
		if(body != null)
		{
			String label = IRcommand.getFreshLabel("end_of_if");
			IR_Code.getInstance().addLine(new IRcommand_Jump_If_Eq_To_Zero(condT,label));
			IR_Code.startScope();
			bodyT = cond.PrintCode();
			IR_Code.endScope();
			IR_Code.getInstance().addLine(new IRcommand_Label(label));
		}
	}
		return null;
	}

	public AST_dec_IF(AST_EXP cond, List<AST_dec> body, int line)
	{
		this.line = line;
		SerialNumber = AST_Node_Serial_Number.getFresh();
		this.cond = cond;
		this.body = body;
	}

	@Override
	public void PrintMe() {
		AST_GRAPHVIZ.getInstance().logNode(SerialNumber,
				"If\nCond, commands");
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
			System.out.format(">> ERROR [%d:%d] condition inside IF is not integral\n",2,2);
			Printer.printError(line);
		}

		/*************************/
		/* [1] Begin Class Scope */
		/*************************/
		SYMBOL_TABLE.getInstance().beginScope("if");

		/***************************/
		/* [2] Semant Data Members */
		/***************************/
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