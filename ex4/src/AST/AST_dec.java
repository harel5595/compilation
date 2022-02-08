package AST;

import TYPES.*;
import IR.*;
import TEMP.*;

public abstract class AST_dec extends AST_Node
{
	/*********************************************************/
	/* The default message for an unknown AST statement node */
	/*********************************************************/
	public TEMP PrintCode()
	{
		System.out.print("UNKNOWN AST STATEMENT NODE\n");return null;
	}
	public void PrintMe()
	{
		System.out.print("UNKNOWN AST STATEMENT NODE\n");
	}

	public int Size()
	{ return -1;}

	public TYPE SemantMe()
	{
		return null;
	}
}
