package AST;

import TYPES.*;

public class AST_arrayTypeDef extends AST_dec {
	public String arrayName;
	public AST_type t;
	
	
	public AST_arrayTypeDef(String arrayName, AST_type t) {
		
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("====================== exp -> exp BINOP exp\n");

		/*******************************/
		/* COPY INPUT DATA MEMBERS ... */
		/*******************************/
		this.arrayName = arrayName;
		this.t = t;
	}

	public void PrintMe()
	{
		
		System.out.print("AST NODE ARRAY TYPE DEF\n");

		/**************************************/
		/* RECURSIVELY PRINT type ... */
		/**************************************/
		if (t != null) t.PrintMe();
		
		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("ARRAYNAME(%s)",arrayName));
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
			/****************************************/
		if (t  != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,t.getSerialNumber());
	}

	public TYPE SemantMe() { return null; } // TODO: finish this func!!
}

