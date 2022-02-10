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

import TEMP.TEMP;
import Useable.UseableClass;

public class IRcommand_LoadArrayElement extends IRcommand
{
	TEMP dst;
	TEMP array_pointer;
	int offset = 0;
	TEMP offset2;

	public IRcommand_LoadArrayElement(TEMP dst, TEMP array_pointer, int offset)
	{
		this.dst      = dst;
		this.array_pointer = array_pointer;
		this.offset = offset;
	}

	public IRcommand_LoadArrayElement(TEMP dst, TEMP array_pointer, TEMP offset2)
	{
		this.dst      = dst;
		this.array_pointer = array_pointer;
		this.offset2 = offset2;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		//TODO: this.

		//MIPSGenerator.getInstance().load(dst,var_name);
	}
}
