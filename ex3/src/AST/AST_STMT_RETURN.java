package AST;

import TYPES.*;
import SYMBOL_TABLE.*;
import SYMBOL_TABLE.SYMBOL_TABLE;

public class AST_STMT_RETURN extends AST_STMT
{
	/****************/
	/* DATA MEMBERS */
	/****************/
	public AST_EXP exp;

	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_STMT_RETURN(AST_EXP exp)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		this.exp = exp;
	}

	/************************************************************/
	/* The printing message for a function declaration AST node */
	/************************************************************/
	public void PrintMe()
	{
		/*************************************/
		/* AST NODE TYPE = AST SUBSCRIPT VAR */
		/*************************************/
		System.out.print("AST NODE STMT RETURN\n");

		/*****************************/
		/* RECURSIVELY PRINT exp ... */
		/*****************************/
		if (exp != null) exp.PrintMe();

		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"RETURN");

		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (exp != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,exp.SerialNumber);
	}

	public TYPE SemantMe()
	{
		TYPE t;
		if(exp != null)
			exp.SemantMe();
		return null;

		/****************************/
		/* [1] Check If Type exists */
		/****************************/
//		t = SYMBOL_TABLE.getInstance().find("lol");
//		if (t == null)
//		{
//			System.out.format(">> ERROR [%d:%d] non existing type %s\n",2,2,"lol");
//			System.exit(0);
//		}
//
//		/**************************************/
//		/* [2] Check That Name does NOT exist */
//		/**************************************/
//		if (SYMBOL_TABLE.getInstance().find("lol") != null)
//		{
//			System.out.format(">> ERROR [%d:%d] variable %s already exists in scope\n",2,2,"lol");
//		}
//
//		/***************************************************/
//		/* [3] Enter the Function Type to the Symbol Table */
//		/***************************************************/
//		SYMBOL_TABLE.getInstance().enter("lol",t);
//
//		/*********************************************************/
//		/* [4] Return value is irrelevant for class declarations */
//		/*********************************************************/
//		return null;
//
	}
}
