package AST;

import TYPES.TYPE;
import TYPES.TYPE_NIL;
import TYPES.TYPE_STRING;

public class AST_EXP_STR extends AST_EXP{
    public String content;
    public AST_EXP_STR(String content, int line)
    {
        this.line = line;
        SerialNumber = AST_Node_Serial_Number.getFresh();
        this.content = content;
    }

    public void PrintMe()
    {
        /*******************************/
        /* AST NODE TYPE = AST INT EXP */
        /*******************************/
        System.out.format("AST string : %s \n", content);

        /*********************************/
        /* Print to AST GRAPHIZ DOT file */
        /*********************************/
        AST_GRAPHVIZ.getInstance().logNode(
                SerialNumber,
                String.format("String %s", content));
    }

    public TYPE SemantMe()
    {
        return TYPE_STRING.getInstance();
    }

}
