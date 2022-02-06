package AST;

import java.util.List;
import java.util.Objects;

import Printer.Printer;
import TYPES.*;
import SYMBOL_TABLE.SYMBOL_TABLE;
import IR.*;

public class AST_func_call extends AST_EXP {
    public String name;
    public AST_VAR var;
    public List<AST_EXP> lexp;

    public AST_func_call(String name, int line) {
        SerialNumber = AST_Node_Serial_Number.getFresh();
        this.name = name;
        this.line = line;
    }

    public AST_func_call(String name, AST_VAR var, int line) {
        this(name, line);
        this.var = var;
    }

    public AST_func_call(String name, AST_VAR var, List<AST_EXP> lexp, int line) {
        this(name, var, line);
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

    public TYPE SemantMe()
    {
        TYPE_FUNCTION func_type = (TYPE_FUNCTION) SYMBOL_TABLE.getInstance().find(name);
        if(func_type == null)
        {
            System.out.format("ERROR: try to call undefined func %s", name);
            Printer.printError(line);
        }

        int counter = 0;
        TYPE_LIST curr = func_type.params;
        while(curr != null)
        {
            if(counter >= lexp.size())
            {
                System.out.format("ERROR: wrong number of params to the func %s", name);
                Printer.printError(line);
                return null;
            }

            if(!Objects.equals(curr.head.name, lexp.get(counter).SemantMe().name))
            {
                System.out.format("ERROR: wrong type of param number %d, to the func %s.", counter + 1,name);
                Printer.printError(line);
            }
            counter ++;
            curr = curr.tail;
        }
        return func_type.returnType;

    }
}
