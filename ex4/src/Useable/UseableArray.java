package Useable;

import AST.AST_dec;
import IR.*;
import MIPS.MIPSGenerator;
import TEMP.*;

import java.util.LinkedList;
import java.util.List;

public class UseableArray extends UseableClass { // this is type of class, all the difference is in constractor, and acsses.

    UseableClass type;

    public UseableArray(String name, UseableClass type) // TODO: write the constractor code as loop over the class constactor.
    {
        super(name, null);
        this.type = type;
    }

    public void CreateConstructor()
            // need to be with while loop that will create each element.
            // this constractor have param! he need to know the size!
    {
        // TODO: this.
        IR_Code construct = new IR_Code();
        construct.addLine(new IRcommand_Label("allocate_" + name));
        TEMP ret_val = TEMP_FACTORY.getInstance().getFreshTEMP();
        TEMP size = TEMP_FACTORY.getInstance().getFreshTEMP(), size_saved = TEMP_FACTORY.getInstance().getFreshTEMP();
        construct.addLine(new IRcommand_LoadParam(size, 1));
        construct.addLine(new IRcommand_Assign_TEMPs(size_saved, size));
        findFieldsForStruct();
        TEMP one = TEMP_FACTORY.getInstance().getFreshTEMP();
        construct.addLine(new IRcommandConstInt(one, 1));
        construct.addLine(new IRcommand_Binop_Add_Integers(size, size, one));
        construct.addLine(new IRcommandConstInt(one, 4));
        construct.addLine(new IRcommand_Binop_Mul_Integers(size, size, one));
        construct.addLine(new IRcommand_Malloc(ret_val, size)); // until here we calc the scape needed for the array
        construct.addLine(new IRcommand_Store(ret_val, size, 0)); // save the len for run time check.
        // TODO: create while that run the constractor of the desiered class
        TEMP iterated = TEMP_FACTORY.getInstance().getFreshTEMP(),
                temp = TEMP_FACTORY.getInstance().getFreshTEMP(),
                address = TEMP_FACTORY.getInstance().getFreshTEMP();
        construct.addLine(new IRcommandConstInt(iterated, 4));
        String Slabel = IRcommand.getFreshLabel("start_of_while");
        IR_Code.getInstance().addLine(new IRcommand_Label(Slabel));
        String Elabel = IRcommand.getFreshLabel("end_of_while");

        IR_Code.getInstance().addLine(new IRcommand_Jump_If_Eq(iterated, size,Elabel));
        construct.addLine(new IRcommand_Binop_Add_Integers(iterated, iterated, one));

        construct.addLine(new IRcommand_AllocateClass(type, temp)); // run the constractor
        construct.addLine(new IRcommand_Binop_Add_Integers(address, iterated, ret_val));
        construct.addLine(new IRcommand_Store(address, temp, 0));

        IR_Code.getInstance().addLine(new IRcommand_Jump_Label(Slabel));
        IR_Code.getInstance().addLine(new IRcommand_Label(Elabel));

        constructor = new UseableFunc("allocate_" + name, "allocate_" + name, ret_val, construct);

        //TEMP t = TEMP_FACTORY.getInstance().getFreshTEMP();
        //construct.addLine(new IRcommand_GetAddressFromLabel(t, "allocated_"+name + "_VT"));
        //construct.addLine(new IRcommand_Store(ret_val, t, 0));
        //for(int i = 0; i < fields_not_func.size(); i++)
        //{
          //  UseableVar u = fields_not_func.get(i);
            //construct.addCode(u.type.CreateInnerConstructor(t));
            //construct.addLine(new IRcommand_Store(ret_val, t, (i + 1) * 4));
        //}
        //construct.addLine(new IRcommand_Return());
        //constructor = new UseableFunc("allocate_" + name, "allocate_" + name, ret_val, construct);
    }


    @Override
    public IR_Code CreateInnerConstructor(TEMP ret)
    // for values inside others - if an array is in another class, then we will create array in lenght 0
    {
        IR_Code construct = new IR_Code();
        TEMP t = TEMP_FACTORY.getInstance().getFreshTEMP();
        construct.addLine(new IRcommand_Malloc(ret, 4));
        construct.addLine(new IRcommandConstInt(t, 0));
        construct.addLine(new IRcommand_Store(ret, t, 0));
        return construct;
    }


    public void CreateVirtualTable()
    {
        // there is no virtual table for an array.
    }

}
