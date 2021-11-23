package AST;

public class AST_new_exp extends  AST_Node{
    public AST_EXP exp;
    public AST_type type;

    /******************/
    /* CONSTRUCTOR(S) */
    /******************/
    public AST_new_exp(AST_type type, AST_EXP exp)
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
    }
    public AST_new_exp(AST_type type)
    {
        /******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
        /* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        System.out.format("====================== AST New_EXP( type:%s)\n",type);

        /*******************************/
        /* COPY INPUT DATA NENBERS ... */
        /*******************************/
        this.type = type;
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
            System.out.format("AST New_EXP( type:%s, value: %s)\n",type,exp );
        else
            System.out.format("AST New_EXP( type:%s, value: %s)\n",type);
        /*********************************/
        /* Print to AST GRAPHIZ DOT file */
        /*********************************/
        if(exp != null)
            AST_GRAPHVIZ.getInstance().logNode(
                SerialNumber,
                String.format("NEW %s(%s)",type, exp));
        else
            AST_GRAPHVIZ.getInstance().logNode(
                    SerialNumber,
                    String.format("NEW %s",type));
    }



}
