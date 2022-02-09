package IR;

import Useable.*;

import java.util.*;

import TEMP.*;

public class IR_Code {
    public static Stack<Useable> toUse = new Stack<Useable>();
    public static List<UseableClass> classes = new LinkedList<UseableClass>();
    public static IR_Code mainCode;
    public static TEMP currentReturnRegister;
    static Stack<IR_Code> instances = new Stack<>();
    public List<IRcommand> code;
    static public List<IR_Code> funcsCode = new LinkedList<>(); // the code of all the functions
    public IR_Code()
    {
     code = new LinkedList<>();
    }
    public List<IRcommand> getCode() {
        return code;
    }
    /*
    add the code in the param to the end of the code that in this class.
     */
    public void addCode(IR_Code to_add){
        code.addAll(to_add.getCode());
    }

    static public void startFunc() // so we know when to stop drop things from the stack
    {
        toUse.push(new Useable("__not_a_name__"));
        instances.push(new IR_Code());
    }

    static public IR_Code endFunc() // drop things from the stack
    {
        while (!Objects.equals(toUse.pop().name, "__not_a_name__")) {}
        return instances.pop();
    }

    static public void startScope() // so we know when to stop drop things from the stack
    {
        toUse.push(new Useable("__not_a_name__"));
    }

    static public void endScope() // drop things from the stack
    {
        while (!Objects.equals(toUse.pop().name, "__not_a_name__")) {}
    }



    static public void addVar(UseableVar var)
    {
        toUse.push(var);
    }

    static public Useable searchForUse(String name)
    {
        Stack<Useable> tempS = new Stack<>();
        Useable a = toUse.pop(), res = null;
        while (!Objects.equals(a.name, name))
        {
            tempS.push(a);
            a = toUse.pop();
        }

        res = a;
        toUse.push(a);
        while (!tempS.empty())
        {
            a = tempS.pop();
            toUse.push(a);
        }

        return res;
    }

    static public void addFunc(IR_Code funcCode, UseableFunc func)
    {
        funcsCode.add(funcCode);
        toUse.push(func);
    }

    static public IR_Code getInstance()
    {
        IR_Code code;
        if (instances.empty())
        {
            defineGlobalFuncs();
            code = new IR_Code();
            instances.push(code);
            return code;
        }
        else
        {
            code = instances.peek();
            return code;
        }
    }
    public void addLine(IRcommand line)
    {
        code.add(line);
    }

    static void defineGlobalFuncs()
    {
        IR_Code code = new IR_Code();
        TEMP t = TEMP_FACTORY.getInstance().getFreshTEMP();
        String label = IRcommand.getFreshLabel("PrintInt");
        code.addLine(new IRcommand_Label(label));
        code.addLine(new IRcommand_LoadParam(t, 1));
        code.addLine(new IRcommand_PrintInt(t));
        toUse.push(new UseableFunc("PrintInt", label, TEMP_FACTORY.getInstance().getFreshTEMP(), code));
        classes.add(new UseableClass("int", new ArrayList<>(), null));
        classes.add(new UseableClass("String", new ArrayList<>(), null));

        // TODO: this.
    }
    public void MIPSmeAsFunc()
    {
        for(IRcommand ir : code)
            ir.MIPSme();
    }

    public void MIPSme() { // TODO: convert all the IR commands into mips.
        for (UseableClass c : classes)
        {
            c.CreateVirtualTable();
        }
        for(IR_Code f : funcsCode)
            f.MIPSmeAsFunc();
        for(Useable u : toUse)
            u.compile();
        (new IRcommand_Label("main")).MIPSme();
        mainCode.MIPSmeAsFunc();
    }
}
