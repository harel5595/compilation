package Useable;
import IR.IR_Code;
import TEMP.*;
public class UseableFunc extends Useable{
    public String startLabel;
    public TEMP retRegister;
    public IR_Code func_code; // use when this func is inside a class
    boolean compiled = false;
    // TODO: add info about the func so we could call it
    public UseableFunc(String name, String startLabel, TEMP ret, IR_Code func_code)
    {
        super(name);
        this.startLabel = startLabel;
        retRegister = ret;
        this.func_code = func_code;
    }

    public void compile()
    {
        if(compiled)
            return;
        func_code.MIPSmeAsFunc();
    }
}
