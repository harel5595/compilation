package AST;

import SYMBOL_TABLE.SYMBOL_TABLE;

import java.util.List;
import java.util.Objects;
import TYPES.*;

public class AST_func_call2 extends AST_dec {
    public String name;
    public AST_VAR var;
    public List<AST_EXP> lexp;

    public AST_func_call2(String name) {
        SerialNumber = AST_Node_Serial_Number.getFresh();
        this.name = name;
    }

    public AST_func_call2(String name, AST_VAR var) {
        this(name);
        this.var = var;
    }

    public AST_func_call2(String name, AST_VAR var, List<AST_EXP> lexp) {
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

    public TYPE SemantMe()
    {
        TYPE_FUNCTION func_type = (TYPE_FUNCTION) SYMBOL_TABLE.getInstance().find(name);
        if(func_type == null)
        {
            System.out.format("ERROR: try to call undefined func %s", name);
        }

        int counter = 0;
        TYPE_LIST curr = func_type.params;
        while(curr != null)
        {
            if(counter >= lexp.size())
            {
                System.out.format("ERROR: wrong number of params to the func %s", name);
                return null;
            }
            counter ++;
            if(!Objects.equals(curr.head.name, lexp.get(counter).SemantMe().name))
            {
                System.out.format("ERROR: wrong type of param number %d, to the func %s.", counter,name);
            }

        }
        return null;

    }
}
