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
        this(type, name);
        this.exp = exp;
    }

    public void PrintMe()
    {
        /********************************************/
        /* AST NODE TYPE = AST ASSIGNMENT STATEMENT */
        /********************************************/
        System.out.print("AST NODE ASSIGN STMT\n");

        /***********************************/
        /* RECURSIVELY PRINT VAR + EXP ... */
        /***********************************/
        if (type != null) type.PrintMe();
        if (exp != null) exp.PrintMe();

        /***************************************/
        /* PRINT Node to AST GRAPHVIZ DOT file */
        /***************************************/
        AST_GRAPHVIZ.getInstance().logNode(
                SerialNumber,
                "ASSIGN\nleft := right\n");

        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,type.SerialNumber);
        AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,exp.SerialNumber);
    }

}
