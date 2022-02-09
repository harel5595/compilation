/***********/
/* PACKAGE */
/***********/
package MIPS;

/*******************/
/* GENERAL IMPORTS */
/*******************/

import TEMP.*;

import java.io.PrintWriter;

import java.util.ArrayList;

import java.util.List;


public class MIPSGenerator
{
	private int WORD_SIZE=4;
	/***********************/
	/* The file writer ... */
	/***********************/
	private PrintWriter fileWriter;
	public ArrayList<String> original;

	/***********************/
	/* The file writer ... */
	/***********************/
	public void finalizeFile()
	{
		fileWriter.print("\tli $v0,10\n");
		fileWriter.print("\tsyscall\n");
		fileWriter.close();
	}
	public void call_func(TEMP t)
	{
		fileWriter.format("\tjal %s\n", t);
		original.add(String.format("\tjal %s\n", t));
	}

	public void call_func_label(String t)
	{
		fileWriter.format("\tjal %s\n", t);
	}

	public void label_into_address(String label, TEMP t)
	{
		fileWriter.format("\tla %s, %s\n", t, label);
	}


	public void print_int(TEMP t)
	{
		int idx=t.getSerialNumber();
		// fileWriter.format("\taddi $a0,Temp_%d,0\n",idx);
		fileWriter.format("\tmove $a0,Temp_%d\n",idx);
		fileWriter.format("\tli $v0,1\n");
		fileWriter.format("\tsyscall\n");
		fileWriter.format("\tli $a0,32\n");
		fileWriter.format("\tli $v0,11\n");
		fileWriter.format("\tsyscall\n");

		original.add(String.format("\tmove $a0,Temp_%d\n",idx));
		original.add("\tli $v0,1\n");
		original.add("\tsyscall\n");
		original.add("\tli $a0,32\n");
		original.add("\tli $v0,11\n");
		original.add("\tsyscall\n");
	}
	//public TEMP addressLocalVar(int serialLocalVarNum)
	//{
	//	TEMP t  = TEMP_FACTORY.getInstance().getFreshTEMP();
	//	int idx = t.getSerialNumber();
	//
	//	fileWriter.format("\taddi Temp_%d,$fp,%d\n",idx,-serialLocalVarNum*WORD_SIZE);
	//	
	//	return t;
	//}
	public void stack_push_const_int(TEMP t,int value)
	{
		int idxdst=t.getSerialNumber();
		fileWriter.format("\tli TEMP_%d,%d\n",idxdst, value);
		fileWriter.format("\tsubu $sp,$sp,4\n");
		fileWriter.format("\tsw TEMP_%d,0($sp)\n",idxdst);

		original.add(String.format("\tli TEMP_%d,%d\n",idxdst, value));
		original.add(String.format("\tsubu $sp,$sp,4\n"));
		original.add(String.format("\tsw TEMP_%d,0($sp)\n",idxdst));
	}

	public void stack_push(TEMP t)
	{
		int idxdst=t.getSerialNumber();
		fileWriter.format("\tsubu $sp,$sp,4\n");
		fileWriter.format("\tsw TEMP_%d,0($sp)\n",idxdst);
	}

	public void load_param_from_stack(int place_from_end, TEMP dst)
	{
		int idxdst=dst.getSerialNumber();
		int real_place = place_from_end * -4;
		fileWriter.format("\tlw TEMP_%d,%d($sp)\n",idxdst, real_place);
	}


	public void func_prologue_stack()
	{
		fileWriter.format("\tsubu $sp,$sp,4\n");
		fileWriter.format("\tsw $ra,0($sp)\n");
		fileWriter.format("\tsubu $sp,$sp,4\n");
		fileWriter.format("\tsw $fp,0($sp)\n");
		fileWriter.format("\tmove $fp,$sp\n");

		original.add(String.format("\tsubu $sp,$sp,4\n"));
		original.add(String.format("\tsw $ra,0($sp)\n"));
		original.add(String.format("\tsubu $sp,$sp,4\n"));
		original.add(String.format("\tsw $fp,0($sp)\n"));
		original.add(String.format("\tmove $fp,$sp\n"));

	}
	public void func_epilogue_stack()
	{
		fileWriter.format("\tmove $sp,$fp\n");
		fileWriter.format("\tlw $fp,0($sp)\n");
		fileWriter.format("\tlw $fp,4($sp)\n");
		fileWriter.format("\taddu $sp,$sp,8\n");

		original.add(String.format("\tmove $sp,$fp\n"));
		original.add(String.format("\tlw $fp,0($sp)\n"));
		original.add(String.format("\tlw $fp,4($sp)\n"));
		original.add(String.format("\taddu $sp,$sp,8\n"));
	}


	public void allocate(String var_name) // by default allocates 4 bytes. if you need more (or less) use big_alloc.
	{
		fileWriter.format(".data\n");
		fileWriter.format("\tglobal_%s: .space 4\n",var_name);

		original.add(String.format(".data\n"));
		original.add(String.format("\tglobal_%s: .space 4\n",var_name));

		fileWriter.format(".text\n");
		original.add(String.format(".text\n"));

	}
	public void big_alloc(String var_name, int len)
	{
		fileWriter.format(".data\n");
		fileWriter.format("\tallocated_%s: .space %d\n",var_name, len);


		original.add(String.format(".data\n"));
		original.add(String.format("\tallocated_%s: .space %d\n",var_name, len));

		fileWriter.format(".text\n");
		original.add(String.format(".text\n"));

	}

	public void my_big_alloc(String var_name, List<Integer> values)
	{
		fileWriter.format(".data\n");
		String res = "\t" + var_name + ": .word ";
		for(int i = 0; i < values.size(); i++)
		{
			res = res + values.get(i).toString();
			if(i + 1 < values.size())
				res = res + ',';
		}
		res = res + '\n';
		fileWriter.format(res);
		fileWriter.format(".text\n");
	}

	public void load(TEMP dst,String var_name)
	{
		int idxdst=dst.getSerialNumber();
		fileWriter.format("\tlw Temp_%d,global_%s\n",idxdst,var_name);

		original.add(String.format("\tlw Temp_%d,global_%s\n",idxdst,var_name));
	}
	public void load_string(TEMP dst,String value)
	{
		int idxdst=dst.getSerialNumber();
		fileWriter.format(".data\n");
		fileWriter.format("\tstr_%d:  .asciiz \"%s\"",idxdst,value);
		fileWriter.format("\tla Temp_%d,str_%d",idxdst,idxdst);


		original.add(String.format(".data\n"));
		original.add(String.format("\tstr_%d:  .asciiz \"%s\"",idxdst,value));
		original.add(String.format("\tla Temp_%d,str_%d",idxdst,idxdst));

		fileWriter.format(".text\n");
		original.add(String.format(".text\n"));

	}
	public void store(String var_name,TEMP src)
	{
		int idxsrc=src.getSerialNumber();
		fileWriter.format("\tsw Temp_%d,global_%s\n",idxsrc,var_name);

		original.add(String.format("\tsw Temp_%d,global_%s\n",idxsrc,var_name));
	}
	public void li(TEMP t,int value)
	{
		int idx=t.getSerialNumber();
		fileWriter.format("\tli Temp_%d,%d\n",idx,value);

		original.add(String.format("\tli Temp_%d,%d\n",idx,value));
	}
	public void add(TEMP dst,TEMP oprnd1,TEMP oprnd2)
	{
		int i1 =oprnd1.getSerialNumber();
		int i2 =oprnd2.getSerialNumber();
		int dstidx=dst.getSerialNumber();

		fileWriter.format("\tadd Temp_%d,Temp_%d,Temp_%d\n",dstidx,i1,i2);

		original.add(String.format("\tadd Temp_%d,Temp_%d,Temp_%d\n",dstidx,i1,i2));
	}
	public void sub(TEMP dst,TEMP oprnd1,TEMP oprnd2)
	{
		int i1 =oprnd1.getSerialNumber();
		int i2 =oprnd2.getSerialNumber();
		int dstidx=dst.getSerialNumber();

		fileWriter.format("\tsub Temp_%d,Temp_%d,Temp_%d\n",dstidx,i1,i2);

		original.add(String.format("\tsub Temp_%d,Temp_%d,Temp_%d\n",dstidx,i1,i2));
	}
	public void mul(TEMP dst,TEMP oprnd1,TEMP oprnd2)
	{
		int i1 =oprnd1.getSerialNumber();
		int i2 =oprnd2.getSerialNumber();
		int dstidx=dst.getSerialNumber();

		fileWriter.format("\tmul Temp_%d,Temp_%d,Temp_%d\n",dstidx,i1,i2);

		original.add(String.format("\tmul Temp_%d,Temp_%d,Temp_%d\n",dstidx,i1,i2));
	}
	public void div(TEMP dst,TEMP oprnd1,TEMP oprnd2)
	{
		int i1 =oprnd1.getSerialNumber();
		int i2 =oprnd2.getSerialNumber();
		int dstidx=dst.getSerialNumber();

		fileWriter.format("\tdiv Temp_%d,Temp_%d,Temp_%d\n",dstidx,i1,i2);

		original.add(String.format("\tdiv Temp_%d,Temp_%d,Temp_%d\n",dstidx,i1,i2));
	}
	public void label(String inlabel)
	{
		if (inlabel.equals("main"))
		{
			fileWriter.format(".text\n");
			fileWriter.format("%s:\n",inlabel);

			original.add(String.format(".text\n"));
			original.add(String.format("%s:\n",inlabel));
		}
		else
		{
			fileWriter.format("%s:\n",inlabel);

			original.add(String.format("%s:\n",inlabel));
		}
	}	
	public void jump(String inlabel, boolean isra)
	{
		if(isra)
		{
			fileWriter.format("\tjr $ra\n");

			original.add(String.format("\tjr $ra\n"));
		}
		else
		{
			fileWriter.format("\tj %s\n",inlabel);

			original.add(String.format("\tj %s\n",inlabel));
		}
	}	
	public void blt(TEMP oprnd1,TEMP oprnd2,String label)
	{
		int i1 =oprnd1.getSerialNumber();
		int i2 =oprnd2.getSerialNumber();
		
		fileWriter.format("\tblt Temp_%d,Temp_%d,%s\n",i1,i2,label);

		original.add(String.format("\tblt Temp_%d,Temp_%d,%s\n",i1,i2,label));
	}
	public void bge(TEMP oprnd1,TEMP oprnd2,String label)
	{
		int i1 =oprnd1.getSerialNumber();
		int i2 =oprnd2.getSerialNumber();
		
		fileWriter.format("\tbge Temp_%d,Temp_%d,%s\n",i1,i2,label);

		original.add(String.format("\tbge Temp_%d,Temp_%d,%s\n",i1,i2,label));
	}
	public void bne(TEMP oprnd1,TEMP oprnd2,String label)
	{
		int i1 =oprnd1.getSerialNumber();
		int i2 =oprnd2.getSerialNumber();
		
		fileWriter.format("\tbne Temp_%d,Temp_%d,%s\n",i1,i2,label);

		original.add(String.format("\tbne Temp_%d,Temp_%d,%s\n",i1,i2,label));
	}
	public void beq(TEMP oprnd1,TEMP oprnd2,String label)
	{
		int i1 =oprnd1.getSerialNumber();
		int i2 =oprnd2.getSerialNumber();
		
		fileWriter.format("\tbeq Temp_%d,Temp_%d,%s\n",i1,i2,label);

		original.add(String.format("\tbeq Temp_%d,Temp_%d,%s\n",i1,i2,label));
	}
	public void beqz(TEMP oprnd1,String label)
	{
		int i1 =oprnd1.getSerialNumber();
				
		fileWriter.format("\tbeq Temp_%d,$zero,%s\n",i1,label);

		original.add(String.format("\tbeq Temp_%d,$zero,%s\n",i1,label));
	}


	public static void CleanCode()
	{
		
	}


	
	/**************************************/
	/* USUAL SINGLETON IMPLEMENTATION ... */
	/**************************************/
	private static MIPSGenerator instance = null;

	/*****************************/
	/* PREVENT INSTANTIATION ... */
	/*****************************/
	protected MIPSGenerator() {}

	/******************************/
	/* GET SINGLETON INSTANCE ... */
	/******************************/
	public static MIPSGenerator getInstance()
	{
		if (instance == null)
		{
			/*******************************/
			/* [0] The instance itself ... */
			/*******************************/
			instance = new MIPSGenerator();

			try
			{
				/*********************************************************************************/
				/* [1] Open the MIPS text file and write data section with error message strings */
				/*********************************************************************************/
				String dirname="./output/";
				String filename=String.format("MIPS.txt");

				/***************************************/
				/* [2] Open MIPS text file for writing */
				/***************************************/
				instance.fileWriter = new PrintWriter(dirname+filename);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}

			/*****************************************************/
			/* [3] Print data section with error message strings */
			/*****************************************************/
			instance.fileWriter.print(".data\n");
			instance.fileWriter.print("string_access_violation: .asciiz \"Access Violation\"\n");
			instance.fileWriter.print("string_illegal_div_by_0: .asciiz \"Illegal Division By Zero\"\n");
			instance.fileWriter.print("string_invalid_ptr_dref: .asciiz \"Invalid Pointer Dereference\"\n");
			instance.fileWriter.format(".text\n");
		}
		CleanCode();
		return instance;
	}
}
