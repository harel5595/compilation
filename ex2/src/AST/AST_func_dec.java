package AST;

import java.util.List;

public class AST_func_dec extends AST_dec{
    public List<AST_type> paramList;
    public AST_type type;
    public String name;
    public List<AST_dec> commands;
    public AST_func_dec(AST_type type, String name, List<AST_dec> list)
    {
        SerialNumber = AST_Node_Serial_Number.getFresh();
        this.type = type;
        this.name = name;
        this.commands = list;
    }
    public AST_func_dec(AST_type type, String name, List<AST_dec> list, List<AST_type> paramList)
    {
        this(type, name, list);
        this.paramList = paramList;
    }
}
