package Useable;

import IR.*;
import MIPS.MIPSGenerator;
import TEMP.TEMP;
import TEMP.TEMP_FACTORY;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class UseableInt extends UseableClass { // this is the class of int, it only for right overwrite.

    public UseableFunc getConstructor() {
        if(constructor == null)
            CreateConstructor();
        return constructor;
    }

    public Useable findField(String name)
    {
        return null; // there are no fields in int
    }


    public UseableInt()
    {
        super("int", null);
        compiled = false;
    }

    public int findFieldOffset(String name)
    {
        throw new Error("don't have this field in class!");
    }


    public List<UseableVar> findFieldsForStruct()
    {
        return null;
    }


    public void CreateConstructor()
    {
        IR_Code construct = new IR_Code();
        construct.addLine(new IRcommand_Label("allocate_" + name));
        TEMP ret_val = TEMP_FACTORY.getInstance().getFreshTEMP();
        construct.addLine(new IRcommandConstInt(ret_val, 0));
        construct.addLine(new IRcommand_Return());
        constructor = new UseableFunc("allocate_" + name, "allocate_" + name, ret_val, construct);
    }

    public IR_Code CreateInnerConstructor(TEMP ret) // for values inside others
    {
        IR_Code construct = new IR_Code();
        construct.addLine(new IRcommandConstInt(ret, 0));
        return construct;
    }


    public void CreateVirtualTable()
    {
        // there is no virtual table for int!
    }
}
