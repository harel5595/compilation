package AST;

public class AST_return extends AST_dec {
    public AST_EXP exp;
    public int line;
    public AST_return(int line)
    {
        SerialNumber = AST_Node_Serial_Number.getFresh();
        this.line = line;
    }
    public AST_return(AST_EXP exp, int line)
    {
        this(line);
        this.exp = exp;

    }

    @Override
    public void PrintMe() {
        AST_GRAPHVIZ.getInstance().logNode(SerialNumber,
                "Return\nValue");
        exp.PrintMe();
        AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, exp.getSerialNumber());
    }
}
