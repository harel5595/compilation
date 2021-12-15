package AST;
import Printer.*;
import SYMBOL_TABLE.SYMBOL_TABLE;
import TYPES.*;
public class AST_arrayTypeDef extends AST_dec {
	public String arrayName;
	public AST_type t;
	
	
	public AST_arrayTypeDef(String arrayName, AST_type t, int line) {
		
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
		Printer.printError(line);
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
			/****************************************/
		if (t  != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,t.getSerialNumber());
	}

	public TYPE SemantMe() {
		TYPE St;

		/****************************/
		/* [1] Check If Type exists */
		/****************************/
		St = SYMBOL_TABLE.getInstance().find(t.type);
		if (St == null || St instanceof TYPE_VOID)
		{
			System.out.format(">> ERROR [%d:%d] non existing type %s\n",2,2,t.type);
			System.exit(0);
		}

		/**************************************/
		/* [2] Check That Name does NOT exist */
		/**************************************/
		if (SYMBOL_TABLE.getInstance().find(arrayName) != null)
		{
			System.out.format(">> ERROR [%d:%d] variable %s already exists in scope\n",2,2,arrayName);

		}

		/***************************************************/
		/* [3] Enter the Function Type to the Symbol Table */
		/***************************************************/
		SYMBOL_TABLE.getInstance().enter(arrayName,St);



		return null; }
}

