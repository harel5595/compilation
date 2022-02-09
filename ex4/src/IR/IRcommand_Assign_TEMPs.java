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

public class IRcommand_Assign_TEMPs extends IRcommand
{
	public TEMP src;
	public TEMP dst;

	public IRcommand_Assign_TEMPs(TEMP dst, TEMP src)
	{
		this.dst = dst;
		this.src = src;
	}
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		// TODO: translate into the right commands
		MIPSGenerator.getInstance().move(dst,src);
	}
}
