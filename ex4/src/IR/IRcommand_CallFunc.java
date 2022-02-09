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
import Useable.*;

import java.util.List;

public class IRcommand_CallFunc extends IRcommand
{
	List<TEMP> t;
	UseableFunc func;
	TEMP ret; // the return value of the func will be saved here

	public IRcommand_CallFunc(List<TEMP> t, UseableFunc func, TEMP ret)
	{
		this.t = t;
		this.func = func;
		this.ret = ret;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	// TODO: don't know why its alist, this might not work properly
	//TODO: need to pass params, we still dont do that.
	public void MIPSme()
	{
		MIPSGenerator.getInstance().call_func(t.get(0));
		MIPSGenerator.getInstance().func_epilogue_stack();
	}
}
