package AST;

import Printer.Printer;
import SYMBOL_TABLE.SYMBOL_TABLE;
import TYPES.TYPE;
import TYPES.TYPE_VOID;
import IR.*;
import TEMP.*;
public class AST_return extends AST_dec {
    public AST_EXP exp;
    public int line;
    public AST_return(int line)
    {
        SerialNumber = AST_Node_Serial_Number.getFresh();
        this.line = line;
    }

    @Override
    public TEMP PrintCode() {
        if(exp == null)
        {
            IR_Code.getInstance().addLine(new IRcommand_Return());
            return null;
        }

        TEMP t = exp.PrintCode();
        IR_Code.getInstance().addLine(new IRcommand_Assign_TEMPs(IR_Code.currentReturnRegister, t));
        IR_Code.getInstance().addLine(new IRcommand_Return());
        return null;
    }



    public AST_return(AST_EXP exp, int line)
    {
        this(line);
        this.exp = exp;
    }

    @Override
    public void PrintMe() {
        AST_GRAPHVIZ.getInstance().logNode(SerialNumber,
                "Return\nValue");
        exp.PrintMe();
        AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, exp.getSerialNumber());
    }

    public TYPE SemantMe() {

        TYPE t;
        if(exp != null)
            t = SYMBOL_TABLE.getInstance().find(exp.SemantMe().name);
        else
            t = new TYPE_VOID();
        if(t == null)
        {
            System.out.format(">> ERROR [%d:%d] non existing type %s\n",2,2,exp.SemantMe().name);
            Printer.printError(line);
        }

        return t;
    }


}
