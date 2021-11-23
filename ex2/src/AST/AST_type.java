package AST;

public class AST_type extends AST_dec{
    public String type;
    public String name;
    public AST_type(String type, String name)
    {
        SerialNumber = AST_Node_Serial_Number.getFresh();
        this.type = type;
        this.name = name;
    }
}
