package AST;

import TYPES.*;
import IR.*;

public abstract class AST_dec extends AST_Node
{
	/*********************************************************/
	/* The default message for an unknown AST statement node */
	/*********************************************************/
	public void PrintMe()
	{
		System.out.print("UNKNOWN AST STATEMENT NODE\n");
	}

	public TYPE SemantMe()
	{
		return null;
	}
}
