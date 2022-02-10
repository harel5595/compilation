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

public class IRcommand_GetAddressForStackVar extends IRcommand
{
	public UseableVar var;
	public TEMP dst;

	public IRcommand_GetAddressForStackVar(TEMP dst, UseableVar var)
	{
		this.dst = dst;
		this.var = var;
	}
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		// TODO: translate into the right commands

		//MIPSGenerator.getInstance().label_into_address(label, dst);
		//MIPSGenerator.getInstance().add(dst,t1,t2);
	}
}
