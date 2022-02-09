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

public class IRcommand_AllocateClass extends IRcommand
{
	String var_name;

	public IRcommand_AllocateClass(String var_name)
	{
		this.var_name = var_name;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		MIPSGenerator.getInstance().allocate(var_name);
	}
}
