package AST;

import Printer.*;
import SYMBOL_TABLE.SYMBOL_TABLE;
import TYPES.TYPE;
import TYPES.TYPE_CLASS;
import TYPES.TYPE_LIST;
import TYPES.TYPE_VOID;
import IR.*;

import java.util.List;
import java.util.Objects;
import TEMP.*;
import Useable.*;

public class AST_VAR_FIELD extends AST_VAR
{
	public AST_VAR var;
	public String fieldName;
	public int line;

	@Override
	public String getName() {
		return var.getName();
	}
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/

	//public

	public TEMP PrintCode()
	{
		// TODO: handle this (I think it is working)
		if(var instanceof AST_VAR_SIMPLE) {
			TEMP dst = TEMP_FACTORY.getInstance().getFreshTEMP();
			UseableVar the_val = (UseableVar) IR_Code.searchForUse(var.getName());
			IR_Code.getInstance().addLine(new IRcommand_LoadField(dst, var.getName(), fieldName, the_val.type));
			return dst;
		}
		else
		{
			throw new Error("don't know what to do.");
		}
	}


	public AST_VAR_FIELD(AST_VAR var,String fieldName, int line)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.format("====================== var -> var DOT ID( %s )\n",fieldName);

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.var = var;
		this.fieldName = fieldName;
		this.line = line;
	}

	/*************************************************/
	/* The printing message for a field var AST node */
	/*************************************************/
	public void PrintMe()
	{
		/*********************************/
		/* AST NODE TYPE = AST FIELD VAR */
		/*********************************/
		System.out.print("AST NODE FIELD VAR\n");

		/**********************************************/
		/* RECURSIVELY PRINT VAR, then FIELD NAME ... */
		/**********************************************/
		if (var != null) var.PrintMe();
		System.out.format("FIELD NAME( %s )\n",fieldName);

		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("FIELD\nVAR\n...->%s",fieldName));
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (var != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,var.getSerialNumber());
	}

	public TYPE SemantMe()
	{
		TYPE t;
		boolean flag = false;

		/****************************/
		/* [1] Check If ClassType exists */
		/****************************/
		t = SYMBOL_TABLE.getInstance().find(var.SemantMe().name);
		if (t == null || t instanceof TYPE_VOID)
		{
			System.out.format(">> ERROR [%d:%d] non existing type %s\n",2,2,var.SemantMe().name);
			Printer.printError(line);
		}
		TYPE_LIST fields = ((TYPE_CLASS)(t)).data_members;
		List<String> names = ((TYPE_CLASS)(t)).data_names;
		int counter = 0;
		while(fields != null)
		{
			if (names.get(counter) != null && names.get(counter).equals(fieldName))
			{
				return fields.head;
			}
			fields = fields.tail;
			counter ++;
		}

		System.out.format(">> ERROR [%d:%d] non existing field %s\n",2,2,fieldName);
		Printer.printError(line);

		return null;

		/****************************************/
		/* [2] return the head that we found*/
		/****************************************/
	}
}
