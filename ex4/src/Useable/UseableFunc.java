package Useable;
import IR.IR_Code;
import TEMP.*;
public class UseableFunc extends Useable{
    public String startLabel;
    public TEMP retRegister;
    public IR_Code func_code; // use when this func is inside a class
    public int params_count;
    boolean compiled = false;
    // TODO: add info about the func so we could call it
    public UseableFunc(String name, String startLabel, TEMP ret, IR_Code func_code)
    {
        super(name);
        this.startLabel = startLabel;
        retRegister = ret;
        this.func_code = func_code;
        params_count = 0;
    }
    public UseableFunc(String name, String startLabel, TEMP ret, IR_Code func_code, int params_count)
    {
        this(name, startLabel, ret, func_code);
        this.params_count = params_count;
    }

    public void compile()
    {
        if(compiled)
            return;
        func_code.MIPSmeAsFunc();
    }
}
