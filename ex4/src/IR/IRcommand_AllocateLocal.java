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
import TEMP.TEMP;
import Useable.UseableInt;
import Useable.UseableVar;

public class IRcommand_AllocateLocal extends IRcommand
{
	UseableVar var;

	public IRcommand_AllocateLocal(UseableVar var)
	{
		this.var = var;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		MIPSGenerator.getInstance().allocate_local(var);
		if(var.type instanceof UseableInt)
		{
			// there is no need to do anything
		}
		else
		{
			MIPSGenerator.getInstance().call_func_label(var.type.getConstructor().startLabel);
			TEMP t = var.type.getConstructor().retRegister;
			MIPSGenerator.getInstance().store_to_stack(t, var.offset);
		}



	}
}
