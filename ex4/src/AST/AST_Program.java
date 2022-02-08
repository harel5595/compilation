package AST;

import java.util.Collections;
import java.util.List;

import SYMBOL_TABLE.SYMBOL_TABLE;
import TYPES.*;
import IR.*;
import TEMP.*;
public class AST_Program extends AST_Node {
    /****************/
    /* DATA MEMBERS */
    /****************/
    public List<AST_dec> l;
    public int line;
    /******************/
    /* CONSTRUCTOR(S) */
    /******************/

    public TEMP PrintCode() {
        IR_Code.getInstance(); // to create the global env
        for (AST_dec a: l) {
            a.PrintCode();
        }
        return null;
    }


    public AST_Program(List<AST_dec> l, int line) {
        /******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();
        this.l = l;
        this.line = line;
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

        /**********************************/
        /* PRINT to AST GRAPHVIZ DOT file */
        /**********************************/
        AST_GRAPHVIZ.getInstance().logNode(
                SerialNumber,
                "Program\n");

        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        if (l != null) {
            for (AST_dec menachem : l) {
				menachem.PrintMe();
                AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, menachem.getSerialNumber());

            }
        }
    }

    public TYPE SemantMe()
    {
        /*************************************/
        /* RECURSIVELY PRINT HEAD + TAIL ... */
        /*************************************/
        Collections.reverse(l);
        for(AST_dec stmt: l) {
            System.out.println("Start to SemantMe!!");
            stmt.SemantMe();
            System.out.println("Done to SemantMe!!");
        }
        return null;
    }

}
