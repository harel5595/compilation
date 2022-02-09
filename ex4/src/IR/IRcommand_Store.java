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

public class IRcommand_Store extends IRcommand
{
	String var_name;
	TEMP src;
	int offset = 0;
	
	public IRcommand_Store(String var_name,TEMP src)
	{
		this.src      = src;
		this.var_name = var_name;
	}

	public IRcommand_Store(String var_name,TEMP src, int offset)
	{
		this.src      = src;
		this.var_name = var_name;
		this.offset = offset;
	}

	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		MIPSGenerator.getInstance().store(var_name,src);
	}
}
