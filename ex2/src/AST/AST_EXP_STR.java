package AST;

public class AST_EXP_STR extends AST_EXP{
    public String content;
    public AST_EXP_STR(String content)
    {
        SerialNumber = AST_Node_Serial_Number.getFresh();
        this.content = content;
    }
}
