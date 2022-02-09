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

public class IRcommand_GetAddressFromLabel extends IRcommand
{
	public String label;
	public TEMP dst;

	public IRcommand_GetAddressFromLabel(TEMP dst, String label)
	{
		this.dst = dst;
		this.label = label;
	}
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		// TODO: translate into the right commands
		MIPSGenerator.getInstance().label_into_address(label, dst);
		//MIPSGenerator.getInstance().add(dst,t1,t2);
	}
}
