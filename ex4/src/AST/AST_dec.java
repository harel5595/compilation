package AST;

import TYPES.*;
import IR.*;

public abstract class AST_dec extends AST_Node
{
	/*********************************************************/
	/* The default message for an unknown AST statement node */
	/*********************************************************/
	public IR_Code PrintCode()
	{
		System.out.print("UNKNOWN AST STATEMENT NODE\n");return null;
	}
	public void PrintMe()
	{
		System.out.print("UNKNOWN AST STATEMENT NODE\n");
	}

	public TYPE SemantMe()
	{
		return null;
	}
}
