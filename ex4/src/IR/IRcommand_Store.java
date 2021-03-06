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

public class IRcommand_Store extends IRcommand
{
	String var_name = null;
	TEMP address_dst = null; // one of them will be null, need to store in the one that is not.
	TEMP src;
	int offset = 0;
	UseableVar var = null;
	
	public IRcommand_Store(String var_name,TEMP src)
	{
		this.src      = src;
		this.var_name = var_name;
	}

	public IRcommand_Store(UseableVar var,TEMP src)
	{
		this.src      = src;
		this.var = var;
	}

	public IRcommand_Store(String var_name,TEMP src, int offset)
	{
		this.src      = src;
		this.var_name = var_name;
		this.offset = offset;
	}

	public IRcommand_Store(TEMP dst_address,TEMP src, int offset)
	{
		this.src      = src;
		this.address_dst = dst_address;
		this.offset = offset;
	}



	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		// TODO: this is not ready! need to be able to store into address and not only into global var.
		if(var != null)
			MIPSGenerator.getInstance().store_to_stack(src, var.offset);
			//MIPSGenerator.getInstance().store(var_name,src, offset);
		else
		{
			MIPSGenerator.getInstance().store_by_address(address_dst, src, offset);
		}
	}
}
