package AST;

import Printer.Printer;
import SYMBOL_TABLE.SYMBOL_TABLE;
import TYPES.*;

import java.util.Objects;

public class AST_dec_ASSIGN extends AST_dec
{
	/***************/
	/*  var := exp */
	/***************/
	public AST_VAR var;
	public AST_EXP exp;

	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_dec_ASSIGN(AST_VAR var, AST_EXP exp, int line)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();
		this.line = line;
		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("====================== stmt -> var ASSIGN exp SEMICOLON\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.var = var;
		this.exp = exp;
	}

	/*********************************************************/
	/* The printing message for an assign statement AST node */
	/*********************************************************/
	@Override
	public void PrintMe()
	{
		/********************************************/
		/* AST NODE TYPE = AST ASSIGNMENT STATEMENT */
		/********************************************/
		System.out.print("AST NODE ASSIGN STMT\n");

		/***********************************/
		/* RECURSIVELY PRINT VAR + EXP ... */
		/***********************************/
		if (var != null) var.PrintMe();
		if (exp != null) exp.PrintMe();

		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"ASSIGN\nleft := right\n");
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,var.getSerialNumber());
		AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,exp.getSerialNumber());
	}

	public TYPE SemantMe()
	{
		TYPE t = var.SemantMe();
		if(t == null)
		{
			System.out.format(">> ERROR [%d:%d] bad assignment type.\n",2,2);
			Printer.printError(line);
			return null;
		}

		if(exp != null)
		{
			TYPE expType = exp.SemantMe();
			if(expType instanceof TYPE_CLASS) {
				while (!Objects.equals(t.name, expType.name) && ((TYPE_CLASS) expType).father != null) {
					expType = ((TYPE_CLASS) expType).father;
				}
			}
			if(!Objects.equals(t.name, expType.name))
			{
				if(expType instanceof TYPE_ARRAY && t instanceof TYPE_ARRAY && Objects.equals(((TYPE_ARRAY) expType).elementsType.name, ((TYPE_ARRAY) t).elementsType.name) && Objects.equals(expType.name, "SUPER-DUPER"))
				{

				}
				else {
					System.out.format(">> ERROR [%d:%d] mismatching assignment types.\n", 2, 2);
					Printer.printError(line);
				}
			}
		}
		//System.out.println("got here.......");
		//SYMBOL_TABLE.getInstance().enter(, t);
		return null;
	}
}
