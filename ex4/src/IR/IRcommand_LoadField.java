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

public class IRcommand_LoadField extends IRcommand
{
	TEMP dst;
	TEMP field_in; // the name of the local var that we acsesing.. need temp.
	String field_name;
	UseableClass classStruct;
	int field_offset;
	boolean returnAddress = false;

	public IRcommand_LoadField(TEMP dst, TEMP field_in, String field_name, UseableClass classStruct)
	{
		this.dst      = dst;
		this.field_name = field_name;
		this.field_in = field_in;
		this.classStruct = classStruct;
		field_offset = classStruct.findFieldOffset(field_name);
	}

	public IRcommand_LoadField(TEMP dst, TEMP field_in, String field_name, UseableClass classStruct, boolean returnAddress)
	{
		this.dst      = dst;
		this.field_name = field_name;
		this.field_in = field_in;
		this.classStruct = classStruct;
		field_offset = classStruct.findFieldOffset(field_name);
		this.returnAddress = returnAddress;
	}
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		//TODO: this.
		if(returnAddress) {
			TEMP t = TEMP_FACTORY.getInstance().getFreshTEMP();
			MIPSGenerator.getInstance().li(t, field_offset);
			MIPSGenerator.getInstance().add(dst, field_in, t);
		}
		else
			MIPSGenerator.getInstance().load_by_address(field_in, dst, field_offset);
		//MIPSGenerator.getInstance().load(dst,var_name);
	}
}
