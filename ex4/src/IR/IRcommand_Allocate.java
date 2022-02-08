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

public class IRcommand_Allocate extends IRcommand
{
	String var_name;
	
	public IRcommand_Allocate(String var_name)
	{
		this.var_name = var_name;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	// TODO: change so we will alocate the right amount of space
	public void MIPSme()
	{
		MIPSGenerator.getInstance().allocate(var_name);
	}
}
