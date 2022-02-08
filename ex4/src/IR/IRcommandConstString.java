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

public class IRcommandConstString extends IRcommand
{
	TEMP t;
	String value;

	public IRcommandConstString(TEMP t, String value)
	{
		this.t = t;
		this.value = value;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	// TODO: change this so we will keep this in normal way.
	public void MIPSme()
	{
		//MIPSGenerator.getInstance().li(t,value);
	}
}
