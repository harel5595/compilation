package AST;

import java.util.List;

public class AST_func_call extends AST_EXP {
    public String name;
    public AST_VAR var;
    public List<AST_EXP> lexp;
    public AST_func_call(String name)
    {
        SerialNumber = AST_Node_Serial_Number.getFresh();
        this.name = name;
    }
    public AST_func_call(String name, AST_VAR var)
    {
        this(name);
        this.var = var;
    }
    public AST_func_call(String name, AST_VAR var, List<AST_EXP> lexp)
    {
        this(name, var);
        this.lexp = lexp;
    }
}
