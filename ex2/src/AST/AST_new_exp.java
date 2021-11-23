package AST;

public class AST_new_exp extends  AST_Node{
    public int value;

    /******************/
    /* CONSTRUCTOR(S) */
    /******************/
    public AST_new_exp(int value)
    {
        /******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
        /* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        System.out.format("====================== exp -> INT( %d )\n", value);

        /*******************************/
        /* COPY INPUT DATA NENBERS ... */
        /*******************************/
        this.value = value;
    }

    /************************************************/
    /* The printing message for an INT EXP AST node */
    /************************************************/
    public void PrintMe()
    {
        /*******************************/
        /* AST NODE TYPE = AST INT EXP */
        /*******************************/
        System.out.format("AST NODE INT( %d )\n",value);

        /*********************************/
        /* Print to AST GRAPHIZ DOT file */
        /*********************************/
        AST_GRAPHVIZ.getInstance().logNode(
                SerialNumber,
                String.format("INT(%d)",value));
    }



}
