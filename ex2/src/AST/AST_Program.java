package AST;

import java.util.List;

public class AST_Program extends AST_Node {
    /****************/
    /* DATA MEMBERS */
    /****************/
    public AST_dec head;
    public AST_Program tail;
    public List<AST_dec> l;
    /******************/
    /* CONSTRUCTOR(S) */

    /******************/
    public AST_Program(AST_dec head, AST_Program tail) {
        /******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
        /* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        if (tail != null) System.out.print("====================== stmts -> stmt stmts\n");
        if (tail == null) System.out.print("====================== stmts -> stmt      \n");

        /*******************************/
        /* COPY INPUT DATA NENBERS ... */
        /*******************************/
        this.head = head;
        this.tail = tail;
    }

    public AST_Program(List<AST_dec> l) {
        /******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();
        this.l = l;
        /***************************************/
        /* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        //if (tail != null) System.out.print("====================== stmts -> stmt stmts\n");
        //if (tail == null) System.out.print("====================== stmts -> stmt      \n");

        /*******************************/
        /* COPY INPUT DATA NENBERS ... */
        /*******************************/
    }

    /******************************************************/
    /* The printing message for a statement list AST node */

    /******************************************************/
    public void PrintMe() {
        /**************************************/
        /* AST NODE TYPE = AST STATEMENT LIST */
        /**************************************/
        System.out.print("AST NODE Program\n");

        /*************************************/
        /* RECURSIVELY PRINT HEAD + TAIL ... */
        /*************************************/
        if (head != null) head.PrintMe();
        if (tail != null) tail.PrintMe();

        /**********************************/
        /* PRINT to AST GRAPHVIZ DOT file */
        /**********************************/
        AST_GRAPHVIZ.getInstance().logNode(
                SerialNumber,
                "Program\n");

        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        if (head != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, head.SerialNumber);
        if (tail != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, tail.SerialNumber);
        if (l != null) {
            for (AST_dec menachem : l) {
				AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, menachem.SerialNumber);
            }
        }
    }


}
