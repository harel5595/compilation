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

import java.util.LinkedList;

public class IRcommand_AllocateArray extends IRcommand
{
	UseableClass type_of_elements;
	TEMP ret;
	int size;

	public IRcommand_AllocateArray(UseableClass type_of_elements, TEMP ret, int size)
	{
		this.type_of_elements = type_of_elements;
		this.ret = ret;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		// call to the constractor as func, and return the address that he is creating.
		//(new IRcommand_CallFunc(new LinkedList<TEMP>(), theClass.getConstructor(), ret)).MIPSme();
		// TODO: this.
	}
}
