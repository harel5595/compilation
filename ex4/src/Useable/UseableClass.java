package Useable;

import IR.*;
import MIPS.MIPSGenerator;
import TEMP.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class UseableClass extends Useable {
    UseableClass father;
    boolean compiled;
    List<Useable> fields = new LinkedList<Useable>();
    UseableFunc constructor;
    List<Pair<UseableFunc, Integer>>  offset_for_vt = null;
    List<UseableVar> fields_not_func = null;

    public UseableClass(String name, List<Useable> fields)
    {
        super(name);
        compiled = false;
        this.fields = fields;
    }
    public UseableClass(String name, List<Useable> fields, UseableClass father)
    {
        this(name, fields);
        this.father = father;
    }

    private List<Pair<UseableFunc, Integer>> calcOffestForVT() // recursivly call to father, so you could overwrite his functions.
    {
        if(offset_for_vt != null)
            return offset_for_vt;

        List<Pair<UseableFunc, Integer>> already_in_place = new LinkedList<Pair<UseableFunc, Integer>>();
        if(father != null)
        {
            already_in_place = new LinkedList<Pair<UseableFunc, Integer>>(father.calcOffestForVT());
        }
        for(Useable func : fields)
        {
            if(func instanceof UseableFunc)
            {
                boolean seen = false;
                for(int i =0; i < already_in_place.size(); i++)
                {
                    Pair<UseableFunc, Integer> f = already_in_place.get(i);
                    if(Objects.equals(f.left.name, func.name))
                    {
                        already_in_place.set(i, new Pair<UseableFunc, Integer>((UseableFunc) func, f.right));
                        seen = true;
                        break;
                    }
                }
                if(!seen)
                {
                    already_in_place.add(new Pair<UseableFunc, Integer>((UseableFunc) func, already_in_place.size() * 4));
                }
            }
        }
        offset_for_vt = already_in_place;
        return already_in_place;
    }

    public List<UseableVar> findFieldsForStruct()
    {
        List<UseableVar> vals = new LinkedList<UseableVar>();
        if(father != null)
            vals = father.findFieldsForStruct();

        for(Useable u : fields)
        {
            if(u instanceof UseableVar)
            {
                boolean seen = false;
                for(UseableVar k : vals)
                {
                    if(Objects.equals(k.name, u.name))
                    {
                        seen = true;
                        break;
                    }
                }
                if(!seen)
                {
                    vals.add((UseableVar) u);
                }
            }
        }
        fields_not_func = vals;
            return vals;
    }


    public void CreateConstructor()
    {
        IR_Code construct = new IR_Code();
        construct.addLine(new IRcommand_Label("allocate_" + name));
        TEMP ret_val = TEMP_FACTORY.getInstance().getFreshTEMP();
        findFieldsForStruct();
        construct.addLine(new IRcommand_Malloc(ret_val, (fields_not_func.size() + 1) * 4));

        TEMP t = TEMP_FACTORY.getInstance().getFreshTEMP();
        for(int i = 0; i < fields_not_func.size(); i++)
        {
            UseableVar u = fields_not_func.get(i);
            construct.addCode(u.type.CreateInnerConstructor(t));
            construct.addLine(new IRcommand_Store(ret_val, t, i * 4));
        }
        construct.addLine(new IRcommand_Return());
        constructor = new UseableFunc("allocate_" + name, "allocate_" + name, ret_val, construct);
    }

    public IR_Code CreateInnerConstructor(TEMP ret) // for values inside others
    {
        IR_Code construct = new IR_Code();
        //construct.addLine(new IRcommand_Label("allocate_" + name));
        findFieldsForStruct();
        construct.addLine(new IRcommand_Malloc(ret, (fields_not_func.size() + 1) * 4));

        TEMP t = TEMP_FACTORY.getInstance().getFreshTEMP();
        for(int i = 0; i < fields_not_func.size(); i++)
        {
            UseableVar u = fields_not_func.get(i);
            construct.addCode(u.type.CreateInnerConstructor(t));
            construct.addLine(new IRcommand_Store(ret, t, i * 4));
        }
        //constructor = new UseableFunc("allocate_" + name, "allocate_" + name, ret_val, construct);
        return construct;
    }


    public void CreateVirtualTable()
    {
        if(compiled || name == "int" || name == "String") // TODO: also handle int and string good.
            return;
        compiled = true;
        calcOffestForVT();
        MIPSGenerator.getInstance().big_alloc(name + "_VT", offset_for_vt.size() * 4);
        List<Integer> l = new LinkedList<>();
        for(Pair<UseableFunc, Integer> pair : offset_for_vt)
        {
            TEMP t = TEMP_FACTORY.getInstance().getFreshTEMP();
            IR_Code.getInstance().addLine(new IRcommand_GetAddressFromLabel(t, pair.left.startLabel));
            IR_Code.getInstance().addLine(new IRcommand_Store(name + "_VT", t, pair.right));
            pair.left.compile();
        }
        CreateConstructor();
        IR_Code.addFunc(constructor.func_code, constructor);
        //MIPSGenerator.getInstance().my_big_alloc("Class_" + name + "_VT", );
    }
}
