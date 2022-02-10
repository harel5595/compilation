package AST;

import Printer.*;
import TYPES.TYPE;
import SYMBOL_TABLE.SYMBOL_TABLE;
import TYPES.TYPE_VOID;
import IR.*;
import TEMP.*;
import Useable.UseableVar;

public class AST_VAR_SIMPLE extends AST_VAR
{
	/************************/
	/* simple variable name */
	/************************/
	public String name;
	public int line;

	@Override
	public String getName() {
		return name;
	}
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/

	public TEMP PrintCode()
	{
		TEMP dst = TEMP_FACTORY.getInstance().getFreshTEMP();
		IR_Code.getInstance().addLine(new IRcommand_Load(dst, (UseableVar) IR_Code.searchForUse(name)));
		return dst;
	}
	@Override
	public TEMP GetAddress()
	{
		// TODO: this maybe wrong. the IRcommand_GetAddressFromLabel might not work with variables on the stack!
		TEMP dst = TEMP_FACTORY.getInstance().getFreshTEMP();
		IR_Code.getInstance().addLine(new IRcommand_GetAddressForStackVar(dst, (UseableVar) IR_Code.searchForUse(name))); // get the address of the var
		return dst;
	}



	public AST_VAR_SIMPLE(String name, int line)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();
	
		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.format("====================== var -> ID( %s )\n",name);

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.name = name;
		this.line = line;
	}

	/**************************************************/
	/* The printing message for a simple var AST node */
	/**************************************************/
	public void PrintMe()
	{
		/**********************************/
		/* AST NODE TYPE = AST SIMPLE VAR */
		/**********************************/
		System.out.format("AST NODE SIMPLE VAR( %s )\n",name);

		/*********************************/
		/* Print to AST GRAPHIZ DOT file */
		/*********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("SIMPLE\nVAR\n(%s)",name));
	}


	public TYPE SemantMe() {

		TYPE t;
		t = SYMBOL_TABLE.getInstance().find(name);
		if(t == null || t instanceof TYPE_VOID)
		{
			System.out.format(">> ERROR [%d:%d] non existing type %s\n",2,2,name);
			Printer.printError(line);
		}
		
		return t;
	}


}
