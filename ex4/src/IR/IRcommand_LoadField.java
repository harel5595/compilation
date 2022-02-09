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

public class IRcommand_LoadField extends IRcommand
{
	TEMP dst;
	String field_in;
	String field_name;
	UseableClass classStruct;

	public IRcommand_LoadField(TEMP dst, String field_in, String field_name, UseableClass classStruct)
	{
		this.dst      = dst;
		this.field_name = field_name;
		this.field_in = field_in;
		this.classStruct = classStruct;
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
