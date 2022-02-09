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
import Useable.UseableClass;

public class IRcommand_StoreField extends IRcommand
{
	TEMP src;
	String field_in;
	String field_name;
	UseableClass classStruct;

	public IRcommand_StoreField(TEMP src, String field_in, String field_name, UseableClass classStruct)
	{
		this.src      = src;
		this.field_name = field_name;
		this.field_in = field_in;
		this.classStruct = classStruct;
	}


	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		// TODO: this.
		//MIPSGenerator.getInstance().store(var_name,src);
	}
}
