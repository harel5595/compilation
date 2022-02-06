package AST;

import SYMBOL_TABLE.SYMBOL_TABLE;
import IR.*;

public class AST_type extends AST_dec{
    public String type;
    public String name;
    public int line;

    @Override
    public IR_Code PrintCode() {
        if(name != null)
            AST_GRAPHVIZ.getInstance().logNode(
                    this.SerialNumber,
                    String.format("Type\n...->%s , %s", type, name));
        else
            AST_GRAPHVIZ.getInstance().logNode(
                    this.SerialNumber,
                    String.format("Type\n...->%s", type));
        return null;
    }

    public AST_type(String type, String name, int line)
    {
        SerialNumber = AST_Node_Serial_Number.getFresh();
        this.type = type;
        this.name = name;
        this.line = line;
    }
    public AST_type(AST_type type, String name,int line)
    {
        SerialNumber = AST_Node_Serial_Number.getFresh();
        this.type = type.type;
        this.name = name;
        this.line = line;
        if(type.name != null)
            throw new RuntimeException("called with type that have name.");
    }

    @Override
    public void PrintMe() {
        if(name != null)
        AST_GRAPHVIZ.getInstance().logNode(
                this.SerialNumber,
                String.format("Type\n...->%s , %s", type, name));
        else
            AST_GRAPHVIZ.getInstance().logNode(
                    this.SerialNumber,
                    String.format("Type\n...->%s", type));
    }
}
