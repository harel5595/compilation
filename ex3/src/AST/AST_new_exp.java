package AST;

import Printer.Printer;
import SYMBOL_TABLE.SYMBOL_TABLE;
import TYPES.TYPE;
import TYPES.TYPE_ARRAY;

import java.util.Objects;

public class AST_new_exp extends  AST_EXP{
    public AST_EXP exp;
    public AST_type type;
    public int line;

    /******************/
    /* CONSTRUCTOR(S) */
    /******************/
    public AST_new_exp(AST_type type, AST_EXP exp, int line)
    {
        /******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
        /* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        System.out.format("====================== AST New_EXP( type:%s, value: %s)\n",type,exp );

        /*******************************/
        /* COPY INPUT DATA NENBERS ... */
        /*******************************/
        this.exp = exp;
        this.type = type;
        this.line = line;
    }
    public AST_new_exp(AST_type type, int line)
    {
        /******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
        /* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        System.out.format("====================== AST New_EXP( type:%s)\n",type.type);

        /*******************************/
        /* COPY INPUT DATA NENBERS ... */
        /*******************************/
        this.type = type;
        this.line = line;
    }

    /************************************************/
    /* The printing message for an INT EXP AST node */
    /************************************************/
    public void PrintMe()
    {
        /*******************************/
        /* AST NODE TYPE = AST INT EXP */
        /*******************************/
        if(exp != null)
            System.out.format("AST New_EXP( type:%s, value: %s)\n",type.type, exp.moish);
        else
            System.out.format("AST New_EXP( type:%s)\n",type.type);
        /*********************************/
        /* Print to AST GRAPHIZ DOT file */
        /*********************************/
        if(type.name != null)
            AST_GRAPHVIZ.getInstance().logNode(
                SerialNumber,
                String.format("NEW %s(%s)",type.type, type.name));
        else
            AST_GRAPHVIZ.getInstance().logNode(
                    SerialNumber,
                    String.format("NEW %s",type.type));
    }

    public TYPE SemantMe(){
        TYPE tempType = SYMBOL_TABLE.getInstance().find(type.type);
        if(tempType == null)
        {
            System.out.format("ERROR: .");
            Printer.printError(line);
        }

        if(exp != null)
        {
            if(!Objects.equals(exp.SemantMe().name, "int")) {
                System.out.format("ERROR: not int in array declerations.");
                Printer.printError(line);
            }
            tempType = new TYPE_ARRAY("SUPER-DUPER", tempType);
        }

        return tempType;
    }

}
