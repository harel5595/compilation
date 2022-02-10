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

public class IRcommand_Jump_If_Eq extends IRcommand
{
	TEMP t1;
	TEMP t2;
	String label_name;

	public IRcommand_Jump_If_Eq(TEMP t1, TEMP t2, String label_name)
	{
		this.t1 = t1;
		this.t2 = t2;
		this.label_name = label_name;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		MIPSGenerator.getInstance().beq(t1,t2,label_name);
	}
}
