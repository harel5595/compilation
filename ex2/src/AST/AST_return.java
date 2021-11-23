package AST;

public class AST_return extends AST_dec {
    public AST_EXP exp;
    public AST_return()
    {
        SerialNumber = AST_Node_Serial_Number.getFresh();
    }
    public AST_return(AST_EXP exp)
    {
        this();
        this.exp = exp;
    }

    @Override
    public void PrintMe() {
        AST_GRAPHVIZ.getInstance().logNode(SerialNumber,
                "Return\nValue");
        exp.PrintMe();
        AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, exp.SerialNumber);
    }
}
