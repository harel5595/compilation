package AST;

import TYPES.*;
import IR.*;
import TEMP.*;

public class AST_dec_Node extends AST_dec{
    public AST_dec head;
    /**
     * @param head the node
     ****************/
    public AST_dec_Node(AST_dec head, int line) {
        SerialNumber = AST_Node_Serial_Number.getFresh();
        this.line = line;
        this.head = head;
    }
    @Override
    public TEMP PrintCode() {
        head.PrintCode();
        return null;
    }

    @Override
    public String getName() {return head.getName();}

    @Override
    public void PrintMe() {
        head.PrintMe();
    }
    @Override
    public int getSerialNumber()
    {
        return head.getSerialNumber();
    }

    @Override
    public TYPE SemantMe() { return head.SemantMe(); }
}
