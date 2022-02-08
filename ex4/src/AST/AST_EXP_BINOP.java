package AST;

import Printer.Printer;
import TEMP.*;
import TYPES.*;
import IR.*;

public class AST_EXP_BINOP extends AST_EXP
{
	int OP;
	public AST_EXP left;
	public AST_EXP right;
	
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/

	public TEMP PrintCode()
	{
		TEMP t1 = null;
		TEMP t2 = null;
		TEMP dst = TEMP_FACTORY.getInstance().getFreshTEMP();

		if (left  != null) t1 = left.PrintCode();
		if (right != null) t2 = right.PrintCode();

		if (OP == 0)
		{
			IR_Code.
					getInstance().
					addLine(new IRcommand_Binop_Add_Integers(dst,t1,t2));
		}
		if (OP == 1)
		{
			IR_Code.
					getInstance().
					addLine(new IRcommand_Binop_Sub_Integers(dst,t1,t2));
		}
		if (OP == 2)
		{
			IR_Code.
					getInstance().
					addLine(new IRcommand_Binop_Mul_Integers(dst,t1,t2));
		}
		if (OP == 3)
		{
			IR_Code.
					getInstance().
					addLine(new IRcommand_Binop_Div_Integers(dst,t1,t2));
		}
		if (OP == 4)
		{
			IR_Code.
					getInstance().
					addLine(new IRcommand_Binop_LT_Integers(dst,t1,t2));
		}
		if (OP == 5) {
			IR_Code.
					getInstance().
					addLine(new IRcommand_Binop_LT_Integers(dst,t2,t1));
		}
		if (OP == 6) {
			IR_Code.
					getInstance().
					addLine(new IRcommand_Binop_EQ_Integers(dst,t1,t2));
		}
		return dst;
	}

	public AST_EXP_BINOP(AST_EXP left,AST_EXP right,int OP, int line)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();
		this.line = line;
		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("====================== exp -> exp BINOP exp\n");

		/*******************************/
		/* COPY INPUT DATA MEMBERS ... */
		/*******************************/
		this.left = left;
		this.right = right;
		this.OP = OP;
	}
	
	/*************************************************/
	/* The printing message for a binop exp AST node */
	/*************************************************/
	public void PrintMe()
	{
		String sOP="";
		
		/*********************************/
		/* CONVERT OP to a printable sOP */
		/*********************************/
		if (OP == 0) {sOP = "+";}
		if (OP == 1) {sOP = "-";}
		if (OP == 2) {sOP = "*";}
		if (OP == 3) {sOP = "/";}
		if (OP == 4) {sOP = "<";}
		if (OP == 5) {sOP = ">";}
		if (OP == 6) {sOP = "=";}

		/*************************************/
		/* AST NODE TYPE = AST BINOP EXP */
		/*************************************/
		System.out.print("AST NODE BINOP EXP\n");

		/**************************************/
		/* RECURSIVELY PRINT left + right ... */
		/**************************************/
		if (left != null) left.PrintMe();
		if (right != null) right.PrintMe();
		
		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("BINOP(%s)",sOP));
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (left  != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,left.getSerialNumber());
		if (right != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,right.getSerialNumber());
	}

	public TYPE SemantMe()
	{
		TYPE t1 = null;
		TYPE t2 = null;

		if (left  != null) t1 = left.SemantMe();
		if (right != null) t2 = right.SemantMe();

		if(OP==3 && right != null && right instanceof AST_EXP_INT  && ((AST_EXP_INT) right).value == 0)
		{
			System.out.format(">> ERROR divide by zero %s\n",((AST_EXP_INT) right).value);
			Printer.printError(line);
		}

		if (t1 == TYPE_INT.getInstance() && t2 == TYPE_INT.getInstance())
		{
			return TYPE_INT.getInstance();
		}

		if(OP == 6)
		{
			if(t1!= null && t2 != null && t1.name.equals(t2.name))
			{
				return TYPE_INT.getInstance();
			}
			if((t1 instanceof TYPE_NIL )|| (t2 instanceof TYPE_NIL))
			{
				return TYPE_INT.getInstance();
			}



		}

		//System.exit(0);
		System.out.format("ERROR: wrong bin operator between %s, %s", t1.name, t2.name);
		Printer.printError(line);
		return null;
	}

}
