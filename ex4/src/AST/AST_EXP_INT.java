package AST;

import TYPES.*;
import IR.*;
import TEMP.*;
public class AST_EXP_INT extends AST_EXP
{
	public int value;
	
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/

	public TEMP PrintCode()
	{
		/*******************************/
		/* AST NODE TYPE = AST INT EXP */
		/*******************************/
		TEMP dst = TEMP_FACTORY.getInstance().getFreshTEMP();
		IR_Code.getInstance().addLine(new IRcommandConstInt(dst,value));
		return dst;
	}


	public AST_EXP_INT(int value, int line)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();
		this.line = line;
		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.format("====================== exp -> INT( %d )\n", value);

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.value = value;
	}

	/************************************************/
	/* The printing message for an INT EXP AST node */
	/************************************************/
	public void PrintMe()
	{
		/*******************************/
		/* AST NODE TYPE = AST INT EXP */
		/*******************************/
		System.out.format("AST NODE INT( %d )\n",value);

		/*********************************/
		/* Print to AST GRAPHIZ DOT file */
		/*********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("INT(%d)",value));
	}

	public TYPE SemantMe()
	{
		return TYPE_INT.getInstance();
	}
}
