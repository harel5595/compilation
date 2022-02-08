package IR;

import Useable.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

public class IR_Code {
    public static Stack<Useable> toUse = new Stack<Useable>();
    static Stack<IR_Code> instances = new Stack<>();
    public List<IRcommand> code = new LinkedList<>();
    static public List<IR_Code> funcsCode = new LinkedList<>(); // the code of all the functions
    public IR_Code()
    {
     code = null;
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
        while (a != null)
        {
            toUse.push(a);
            a = tempS.pop();
        }

        return res;
    }

    static public void addFunc(IR_Code funcCode, UseableFunc func)
    {
        funcsCode.add(funcCode);
        toUse.push(func);
    }

    static private int curr = 0;
    static public String getNewRegisterName()
    {
        return "t" + curr++;
    }
    static public IR_Code getInstance()
    {
        IR_Code code;
        if (instances.empty())
        {
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

}
