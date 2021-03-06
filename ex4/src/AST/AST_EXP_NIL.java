package AST;

import TYPES.*;
import IR.*;
import TEMP.*;
public class AST_EXP_NIL extends AST_EXP {


    /******************/
    /* CONSTRUCTOR(S) */
    /******************/

    public TEMP PrintCode()
    {
        TEMP t = TEMP_FACTORY.getInstance().getFreshTEMP();
        IR_Code.getInstance().addLine(new IRcommandConstNil(t));
        return t;
    }

    public AST_EXP_NIL(int line)
    {
        /******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();
        this.line = line;
        /***************************************/
        /* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        System.out.format("====================== exp -> nil\n");

        /*******************************/
        /* COPY INPUT DATA NENBERS ... */
        /*******************************/
    }

    /************************************************/
    /* The printing message for an INT EXP AST node */
    /************************************************/
    public void PrintMe()
    {
        /*******************************/
        /* AST NODE TYPE = AST INT EXP */
        /*******************************/
        System.out.format("AST NODE NIL\n");

        /*********************************/
        /* Print to AST GRAPHIZ DOT file */
        /*********************************/
        AST_GRAPHVIZ.getInstance().logNode(
                SerialNumber,
                "NIL");
    }

    public TYPE SemantMe()
    {
        return TYPE_NIL.getInstance();
    }


}