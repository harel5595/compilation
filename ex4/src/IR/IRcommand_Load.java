/***********/
/* PACKAGE */
/***********/
package IR;

/*******************/
/* GENERAL IMPORTS */
/*******************/

/*******************/
/* PROJECT IMPORTS */
/*******************/

import MIPS.*;
import TEMP.*;
import Useable.UseableVar;

public class IRcommand_Load extends IRcommand
{
	TEMP dst;
	UseableVar var;
	
	public IRcommand_Load(TEMP dst,UseableVar var)
	{
		this.dst      = dst;
		this.var = var;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		// TODO: load from the stack with the right offset.
		//MIPSGenerator.getInstance().load(dst,);
	}
}
