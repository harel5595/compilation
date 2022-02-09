/***********/
/* PACKAGE */
/***********/
package MIPS;

/*******************/
/* GENERAL IMPORTS */
/*******************/

import TEMP.*;

import java.io.PrintWriter;

import java.util.*;


public class MIPSGenerator
{
	private int WORD_SIZE=4;
	/***********************/
	/* The file writer ... */
	/***********************/
	private PrintWriter fileWriter;
	public ArrayList<String> original = new ArrayList<>();
	public int lineNum = 1;
	public Map<Integer, Set<Integer>> alive = new HashMap<>();

	/***********************/
	/* The file writer ... */
	/***********************/
	public void finalizeFile()
	{
		fileWriter.print("\tli $v0,10\n");
		fileWriter.print("\tsyscall\n");
		fileWriter.close();
		/************************************/
		//this is the clean code func, it does th coloring of the registers
		/************************************/



		for(int i = this.lineNum; i > 1; i --)
		{
			if(original.get(i - 1).contains("li"))
			{
				alive.get(i).add(original.get(i - 1).indexOf("Temp")+1);
			}
		}

		//////end//////


	}
	public void call_func(TEMP t)
	{
		fileWriter.format("\tjal %s\n", t);
		original.add(String.format("%d\tjal %s\n",lineNum, t));
		lineNum ++;
	}

	public void call_func_label(String t)
	{
		fileWriter.format("\tjal %s\n", t);
	}

	public void label_into_address(String label, TEMP t)
	{
		int idx=t.getSerialNumber();
		fileWriter.format("\tla Temp_%d, %s\n", idx, label);
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

		original.add(String.format("%d\tmove $a0,Temp_%d\n",lineNum,idx));
		lineNum++;
		original.add(String.format("%d\tli $v0,1\n",lineNum));
		lineNum++;
		original.add(String.format("%d\tsyscall\n",lineNum));
		lineNum++;
		original.add(String.format("%d\tli $a0,32\n",lineNum));
		lineNum++;
		original.add(String.format("%d\tli $v0,11\n",lineNum));
		lineNum++;
		original.add(String.format("%d\tsyscall\n",lineNum));
		lineNum++;
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
	public void stack_push(TEMP t,int value)
	{
		int idxdst=t.getSerialNumber();
		fileWriter.format("\tli Temp_%d,%d\n",idxdst, value);
		fileWriter.format("\tsubu $sp,$sp,4\n");
		fileWriter.format("\tsw Temp_%d,0($sp)\n",idxdst);

		original.add(String.format("%d\tli Temp_%d,%d\n",lineNum,idxdst, value));
		lineNum++;
		original.add(String.format("%d\tsubu $sp,$sp,4\n",lineNum));
		lineNum++;
		original.add(String.format("%d\tsw Temp_%d,0($sp)\n",lineNum,idxdst));
		lineNum++;
	}

	public void stack_push(TEMP t)
	{
		int idxdst=t.getSerialNumber();
		fileWriter.format("\tsubu $sp,$sp,4\n");
		fileWriter.format("\tsw Temp_%d,0($sp)\n",idxdst);

		original.add(String.format("%d\tsubu $sp,$sp,4\n",lineNum));
		lineNum++;
		original.add(String.format("%d\tsw Temp_%d,0($sp)\n",lineNum,idxdst));
		lineNum++;
	}

	public void load_param_from_stack(int place_from_end, TEMP dst)
	{
		int idxdst=dst.getSerialNumber();
		int real_place = place_from_end * -4;
		fileWriter.format("\tlw Temp_%d,%d($sp)\n",idxdst, real_place);

		original.add(String.format("%d\tlw Temp_%d,%d($sp)\n",lineNum,idxdst, real_place));
		lineNum++;
	}


	public void func_prologue_stack()
	{
		fileWriter.format("\tsubu $sp,$sp,4\n");
		fileWriter.format("\tsw $ra,0($sp)\n");
		fileWriter.format("\tsubu $sp,$sp,4\n");
		fileWriter.format("\tsw $fp,0($sp)\n");
		fileWriter.format("\tmove $fp,$sp\n");

		original.add(String.format("%d\tsubu $sp,$sp,4\n",lineNum));
		lineNum++;
		original.add(String.format("%d\tsw $ra,0($sp)\n",lineNum));
		lineNum++;
		original.add(String.format("%d\tsubu $sp,$sp,4\n",lineNum));
		lineNum++;
		original.add(String.format("%d\tsw $fp,0($sp)\n",lineNum));
		lineNum++;
		original.add(String.format("%d\tmove $fp,$sp\n",lineNum));
		lineNum++;

	}

	public void load_params(int num)
	{
		int counter = 8;
		for(int i = 0; i < num; i ++ )
		{
			fileWriter.format("\tlw $TEMP_%d,%d($fp),4\n", i, counter);
			original.add(String.format("%d\tlw $TEMP_%d,%d($fp),4\n",lineNum, i, counter));
			lineNum++;
			counter +=4;
		}
	}

	public void func_epilogue_stack()
	{
		fileWriter.format("\tmove $sp,$fp\n");
		fileWriter.format("\tlw $fp,0($sp)\n");
		fileWriter.format("\tlw $fp,4($sp)\n");
		fileWriter.format("\taddu $sp,$sp,8\n");

		original.add(String.format("%d\tmove $sp,$fp\n",lineNum));
		lineNum++;
		original.add(String.format("%d\tlw $fp,0($sp)\n",lineNum));
		lineNum++;
		original.add(String.format("%d\tlw $fp,4($sp)\n",lineNum));
		lineNum++;
		original.add(String.format("%d\taddu $sp,$sp,8\n",lineNum));
		lineNum++;
	}

	public void move(TEMP dst,TEMP src)
	{
		fileWriter.format("\tmov Temp_%d,Temp_%d\n",dst.getSerialNumber(), src.getSerialNumber());

		original.add(String.format("%d\tmov Temp_%d,Temp_%d\n",lineNum,dst.getSerialNumber(), src.getSerialNumber()));
		lineNum++;
	}


	public void allocate(String var_name) // by default allocates 4 bytes. if you need more (or less) use big_alloc.
	{
		fileWriter.format(".data\n");
		fileWriter.format("\tglobal_%s: .space 4\n",var_name);

		original.add(String.format("%d.data\n",lineNum));
		lineNum++;
		original.add(String.format("%d\tglobal_%s: .space 4\n",lineNum,var_name));
		lineNum++;

		fileWriter.format(".text\n");
		original.add(String.format("%d.text\n",lineNum));
		lineNum++;

	}
	public void big_alloc(String var_name, int len)
	{
		fileWriter.format(".data\n");
		fileWriter.format("\tallocated_%s: .space %d\n",var_name, len);


		original.add(String.format("%d.data\n",lineNum));
		lineNum++;
		original.add(String.format("%d\tallocated_%s: .space %d\n",lineNum,var_name, len));
		lineNum++;

		fileWriter.format(".text\n");
		original.add(String.format("%d.text\n",lineNum));
		lineNum++;

	}

	public void my_big_alloc(String var_name, List<Integer> values)
	{
		fileWriter.format(".data\n");

		original.add(String.format("%d.data\n",lineNum));
		lineNum++;
		String res = "\t" + var_name + ": .word ";
		for(int i = 0; i < values.size(); i++)
		{
			res = res + values.get(i).toString();
			if(i + 1 < values.size())
				res = res + ',';
		}
		res = res + '\n';
		fileWriter.format(res);
		original.add(String.format("%dres",lineNum));
		lineNum++;
		fileWriter.format(".text\n");
		original.add(String.format("%d.text\n",lineNum));
		lineNum++;
	}

	public void load(TEMP dst,String var_name)
	{
		int idxdst=dst.getSerialNumber();
		fileWriter.format("\tlw Temp_%d,global_%s\n",idxdst,var_name);

		original.add(String.format("%d\tlw Temp_%d,global_%s\n",lineNum,idxdst,var_name));
		lineNum++;
	}

	public void load_with_offset(TEMP dst,String var_name, int offset)
	{
		int idxdst=dst.getSerialNumber();
		fileWriter.format("\tlw Temp_%d,%d(global_%s)\n",idxdst,offset,var_name);

		original.add(String.format("%d\tlw Temp_%d,%d(global_%s)\n",lineNum,idxdst,offset,var_name));
		lineNum++;
	}

	public void load_string(TEMP dst,String value)
	{
		int idxdst=dst.getSerialNumber();
		fileWriter.format(".data\n");
		fileWriter.format("\tstr_%d:  .asciiz \"%s\"",idxdst,value);
		fileWriter.format("\tla Temp_%d,str_%d",idxdst,idxdst);


		original.add(String.format("%d.data\n",lineNum));
		lineNum++;
		original.add(String.format("%d\tstr_%d:  .asciiz \"%s\"",lineNum,idxdst,value));
		lineNum++;
		original.add(String.format("%d\tla Temp_%d,str_%d",lineNum,idxdst,idxdst));
		lineNum++;

		fileWriter.format(".text\n");
		original.add(String.format("%d.text\n",lineNum));
		lineNum++;

	}
	public void store(String var_name,TEMP src)
	{
		int idxsrc=src.getSerialNumber();
		fileWriter.format("\tsw Temp_%d,global_%s\n",idxsrc,var_name);

		original.add(String.format("%d\tsw Temp_%d,global_%s\n",lineNum,idxsrc,var_name));
		lineNum++;
	}

	public void store_with_offset(String var_name,TEMP src, int offset)
	{
		int idxsrc=src.getSerialNumber();
		fileWriter.format("\tsw Temp_%d,%d(global_%s)\n",idxsrc,offset,var_name);

		original.add(String.format("%d\tsw Temp_%d,%d(global_%s)\n",lineNum,idxsrc,offset,var_name));
		lineNum++;
	}

	public void li(TEMP t,int value)
	{
		int idx=t.getSerialNumber();
		fileWriter.format("\tli Temp_%d,%d\n",idx,value);

		original.add(String.format("%d\tli Temp_%d,%d\n",lineNum,idx,value));
		lineNum++;
	}
	public void add(TEMP dst,TEMP oprnd1,TEMP oprnd2)
	{
		int i1 =oprnd1.getSerialNumber();
		int i2 =oprnd2.getSerialNumber();
		int dstidx=dst.getSerialNumber();

		fileWriter.format("\tadd Temp_%d,Temp_%d,Temp_%d\n",dstidx,i1,i2);

		original.add(String.format("%d\tadd Temp_%d,Temp_%d,Temp_%d\n",lineNum,dstidx,i1,i2));
		lineNum++;
	}
	public void sub(TEMP dst,TEMP oprnd1,TEMP oprnd2)
	{
		int i1 =oprnd1.getSerialNumber();
		int i2 =oprnd2.getSerialNumber();
		int dstidx=dst.getSerialNumber();

		fileWriter.format("\tsub Temp_%d,Temp_%d,Temp_%d\n",dstidx,i1,i2);

		original.add(String.format("%d\tsub Temp_%d,Temp_%d,Temp_%d\n",lineNum,dstidx,i1,i2));
		lineNum++;
	}
	public void mul(TEMP dst,TEMP oprnd1,TEMP oprnd2)
	{
		int i1 =oprnd1.getSerialNumber();
		int i2 =oprnd2.getSerialNumber();
		int dstidx=dst.getSerialNumber();

		fileWriter.format("\tmul Temp_%d,Temp_%d,Temp_%d\n",dstidx,i1,i2);

		original.add(String.format("%d\tmul Temp_%d,Temp_%d,Temp_%d\n",lineNum,dstidx,i1,i2));
		lineNum++;
	}
	public void div(TEMP dst,TEMP oprnd1,TEMP oprnd2)
	{
		int i1 =oprnd1.getSerialNumber();
		int i2 =oprnd2.getSerialNumber();
		int dstidx=dst.getSerialNumber();

		fileWriter.format("\tdiv Temp_%d,Temp_%d,Temp_%d\n",dstidx,i1,i2);

		original.add(String.format("%d\tdiv Temp_%d,Temp_%d,Temp_%d\n",lineNum,dstidx,i1,i2));
		lineNum++;
	}
	public void label(String inlabel)
	{
		if (inlabel.equals("main"))
		{
			fileWriter.format(".text\n");
			fileWriter.format("%s:\n",inlabel);

			original.add(String.format("%d.text\n",lineNum));
			lineNum++;
			original.add(String.format("%d%s:\n",lineNum,inlabel));
			lineNum++;
		}
		else
		{
			fileWriter.format("%s:\n",inlabel);

			original.add(String.format("%d%s:\n",lineNum,inlabel));
			lineNum++;
		}
	}	
	public void jump(String inlabel, boolean isra)
	{
		if(isra)
		{
			fileWriter.format("\tjr $ra\n");

			original.add(String.format("%d\tjr $ra\n",lineNum));
			lineNum++;
		}
		else
		{
			fileWriter.format("\tj %s\n",inlabel);

			original.add(String.format("%d\tj %s\n",lineNum,inlabel));
			lineNum++;
		}
	}	
	public void blt(TEMP oprnd1,TEMP oprnd2,String label)
	{
		int i1 =oprnd1.getSerialNumber();
		int i2 =oprnd2.getSerialNumber();
		
		fileWriter.format("\tblt Temp_%d,Temp_%d,%s\n",i1,i2,label);

		original.add(String.format("%d\tblt Temp_%d,Temp_%d,%s\n",lineNum,i1,i2,label));
		lineNum++;
	}
	public void bge(TEMP oprnd1,TEMP oprnd2,String label)
	{
		int i1 =oprnd1.getSerialNumber();
		int i2 =oprnd2.getSerialNumber();
		
		fileWriter.format("\tbge Temp_%d,Temp_%d,%s\n",i1,i2,label);

		original.add(String.format("%d\tbge Temp_%d,Temp_%d,%s\n",lineNum,i1,i2,label));
		lineNum++;
	}
	public void bne(TEMP oprnd1,TEMP oprnd2,String label)
	{
		int i1 =oprnd1.getSerialNumber();
		int i2 =oprnd2.getSerialNumber();
		
		fileWriter.format("\tbne Temp_%d,Temp_%d,%s\n",i1,i2,label);

		original.add(String.format("%d\tbne Temp_%d,Temp_%d,%s\n",lineNum,i1,i2,label));
		lineNum++;
	}
	public void beq(TEMP oprnd1,TEMP oprnd2,String label)
	{
		int i1 =oprnd1.getSerialNumber();
		int i2 =oprnd2.getSerialNumber();
		
		fileWriter.format("\tbeq Temp_%d,Temp_%d,%s\n",i1,i2,label);

		original.add(String.format("%d\tbeq Temp_%d,Temp_%d,%s\n",lineNum,i1,i2,label));
		lineNum++;
	}
	public void beqz(TEMP oprnd1,String label)
	{
		int i1 =oprnd1.getSerialNumber();
				
		fileWriter.format("\tbeq Temp_%d,$zero,%s\n",i1,label);

		original.add(String.format("%d\tbeq Temp_%d,$zero,%s\n",lineNum,i1,label));
		lineNum++;
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
		return instance;
	}
}
