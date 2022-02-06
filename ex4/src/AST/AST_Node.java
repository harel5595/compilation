package AST;

import TYPES.*;
import IR.*;

public abstract class AST_Node
{
	/*******************************************/
	/* The serial number is for debug purposes */
	/* In particular, it can help in creating  */
	/* a graphviz dot format of the AST ...    */
	/*******************************************/
	public int SerialNumber;
	public int line;

	public String getName() { return null;}

	/***********************************************/
	/* The default message for an unknown AST node */
	/***********************************************/
	public IR_Code PrintCode()
	{
		System.out.print("AST NODE UNKNOWN\n");
		return null;
	}
	public void PrintMe()
	{
		System.out.print("AST NODE UNKNOWN\n");
	}
	public int getSerialNumber() {return SerialNumber;}

    public TYPE SemantMe() { return null; }
}
