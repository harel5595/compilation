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
		MIPSGenerator.getInstance().call_func_label("allocate_" + var.type.getConstructor().startLabel);
		TEMP t = var.type.getConstructor().retRegister;
		MIPSGenerator.getInstance().store_to_stack(t, var.offset);

	}
}
