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
	int size = 0;
	TEMP size2;


	public IRcommand_Malloc(TEMP address, int size)
	{
		this.size = size;
		this.address = address;
	}

	public IRcommand_Malloc(TEMP address, TEMP size)
	{
		this.size2 = size;
		this.address = address;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		if(size != 0)
			MIPSGenerator.getInstance().malloc(address, size);
		else
			MIPSGenerator.getInstance().malloc_from_temp(address, size2);
	}
}
