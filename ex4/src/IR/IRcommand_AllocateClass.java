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
import Useable.UseableClass;

import java.util.LinkedList;

public class IRcommand_AllocateClass extends IRcommand
{
	UseableClass theClass;
	TEMP ret;

	public IRcommand_AllocateClass(UseableClass type, TEMP ret)
	{
		this.theClass = type;
		this.ret = ret;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		// call to the constractor as func, and return the address that he is creating.
		(new IRcommand_CallFunc(new LinkedList<TEMP>(), theClass.getConstructor(), ret)).MIPSme();

	}
}
