package AST;

import Printer.*;
import SYMBOL_TABLE.SYMBOL_TABLE;
import TYPES.*;

public class AST_VAR_SUBSCRIPT extends AST_VAR {
    public AST_VAR var;
    public AST_EXP subscript;
    public int line;

    @Override
    public String getName() {
        return var.getName();
    }

    /******************/
    /* CONSTRUCTOR(S) */
    /******************/
    public AST_VAR_SUBSCRIPT(AST_VAR var, AST_EXP subscript, int line) {
        /******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
        /* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        System.out.print("====================== var -> var [ exp ]\n");

        /*******************************/
        /* COPY INPUT DATA NENBERS ... */
        /*******************************/
        this.var = var;
        this.subscript = subscript;
        this.line = line;
    }

    /*****************************************************/
    /* The printing message for a subscript var AST node */

    /*****************************************************/
    public void PrintMe() {
        /*************************************/
        /* AST NODE TYPE = AST SUBSCRIPT VAR */
        /*************************************/
        System.out.print("AST NODE SUBSCRIPT VAR\n");

        /****************************************/
        /* RECURSIVELY PRINT VAR + SUBSRIPT ... */
        /****************************************/
        if (var != null) var.PrintMe();
        if (subscript != null) subscript.PrintMe();

        /***************************************/
        /* PRINT Node to AST GRAPHVIZ DOT file */
        /***************************************/
        AST_GRAPHVIZ.getInstance().logNode(
                SerialNumber,
                "SUBSCRIPT\nVAR\n...[...]");

        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        if (var != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, var.getSerialNumber());
        if (subscript != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, subscript.getSerialNumber());
    }


    public TYPE SemantMe() {

        TYPE t = null;

        t = SYMBOL_TABLE.getInstance().find(var.getName());

        if (t == null || t instanceof TYPE_VOID) {
            System.out.format(">> ERROR [%d:%d] non existing type %s\n", 2, 2, var.SemantMe().name);
            Printer.printError(line);
        }

        if (!t.isArray()) {
            System.out.format(">> ERROR [%d:%d] not an array %s\n", 2, 2, var.SemantMe().name);
            Printer.printError(line);
        }

        if(var instanceof AST_VAR_SUBSCRIPT)
            return ((TYPE_ARRAY)var.SemantMe()).elementsType;

        return ((TYPE_ARRAY)t).elementsType;
    }

}
