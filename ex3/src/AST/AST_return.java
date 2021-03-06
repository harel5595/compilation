package AST;

import Printer.Printer;
import SYMBOL_TABLE.SYMBOL_TABLE;
import TYPES.TYPE;
import TYPES.TYPE_VOID;

import java.util.Objects;

public class AST_return extends AST_dec {
    public AST_EXP exp;
    public int line;
    public AST_return(int line)
    {
        SerialNumber = AST_Node_Serial_Number.getFresh();
        this.line = line;
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
        t = SYMBOL_TABLE.getInstance().find(exp.SemantMe().name);
        if(t == null || t instanceof TYPE_VOID)
        {
            System.out.format(">> ERROR [%d:%d] non existing type %s\n",2,2,exp.SemantMe().name);
            Printer.printError(line);
            return null;
        }

        if(SYMBOL_TABLE.returnType == null|| !Objects.equals(SYMBOL_TABLE.returnType.name, t.name))
        {
            System.out.format("ERROR: wrong out (for bogin: return) type.");
            Printer.printError(line);
            return null;
        }

        return t;
    }


}
