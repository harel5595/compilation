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

import MIPS.MIPSGenerator;

public class IRcommand_Return extends IRcommand
{
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		//TODO: need to pass params, we still dont do that.
		MIPSGenerator.getInstance().jump("this doesnt have any effect", true);
		MIPSGenerator.getInstance().func_prologue_stack(); // updates stack
	}
}
