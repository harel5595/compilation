package AST;

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
    public void PrintMe() {
        head.PrintMe();
    }
    @Override
    public int getSerialNumber()
    {
        return head.getSerialNumber();
    }

}
