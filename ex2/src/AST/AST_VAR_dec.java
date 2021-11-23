package AST;

public class AST_VAR_dec extends AST_dec{
    public AST_type type;
    public String name;
    public AST_Node exp;
    public AST_VAR_dec(AST_type type ,String name)
    {
        this.type = type;
        this.name = name;
        SerialNumber  = AST_Node_Serial_Number.getFresh();
    }

    public AST_VAR_dec(AST_type type ,String name, AST_Node exp)
    {
        this.type = type;
        this.name = name;
        this.exp = exp;
        SerialNumber  = AST_Node_Serial_Number.getFresh();
    }
}
