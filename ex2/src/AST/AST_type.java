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
    public AST_type(AST_type type, String name)
    {
        SerialNumber = AST_Node_Serial_Number.getFresh();
        this.type = type.type;
        this.name = name;
        if(type.name != null)
            throw new RuntimeException("called with type that have name.");
    }

    @Override
    public void PrintMe() {
        if(name != null)
        AST_GRAPHVIZ.getInstance().logNode(
                this.SerialNumber,
                String.format("Type\n...->%s , %s", type, name));
        else
            AST_GRAPHVIZ.getInstance().logNode(
                    this.SerialNumber,
                    String.format("Type\n...->%s", type));
    }
}
