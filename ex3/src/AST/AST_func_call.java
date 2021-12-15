package AST;

import java.util.List;

public class AST_func_call extends AST_EXP {
    public String name;
    public AST_VAR var;
    public List<AST_EXP> lexp;

    public AST_func_call(String name) {
        SerialNumber = AST_Node_Serial_Number.getFresh();
        this.name = name;
    }

    public AST_func_call(String name, AST_VAR var) {
        this(name);
        this.var = var;
    }

    public AST_func_call(String name, AST_VAR var, List<AST_EXP> lexp) {
        this(name, var);
        this.lexp = lexp;
    }

    public void PrintMe() {

        /*********************************/
        /* AST NODE TYPE = AST FIELD VAR */
        /*********************************/
        System.out.print("function_call\n");

        /**********************************************/
        /* RECURSIVELY PRINT VAR, then FIELD NAME ... */
        /**********************************************/
        if (var != null) var.PrintMe();
        System.out.format("Function Name( %s )\n", name);

        /***************************************/
        /* PRINT Node to AST GRAPHVIZ DOT file */
        /***************************************/
        AST_GRAPHVIZ.getInstance().logNode(
                SerialNumber,
                String.format("Function\nName\n...->%s", name));

        if(lexp != null)
        for (AST_EXP exp :
                lexp) {
            exp.PrintMe();
            AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, exp.getSerialNumber());

        }


        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        if (var != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, var.getSerialNumber());
    }
}
