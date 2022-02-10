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
import TEMP.*;

public class IRcommand_Malloc extends IRcommand
{

	TEMP address;
	int size;


	public IRcommand_Malloc(TEMP address, int size)
	{
		this.size = size;
		this.address = address;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		MIPSGenerator.getInstance().malloc(address, size);
	}
}
