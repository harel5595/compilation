package AST;

import java.util.List;

public class AST_class extends AST_dec {
    /***************/
    /*  var := exp */
    /***************/
    public String ID;
    public List<AST_dec> fields;
    public String name2;

    /*******************/
    /*  CONSTRUCTOR(S) */

    /*******************/


    public AST_class(String ID, List<AST_dec> fields) {
        /******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
        /* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        System.out.format("====================== var -> var DOT ID( %s )\n", ID);

        /*******************************/
        /* COPY INPUT DATA NENBERS ... */
        /*******************************/
        this.ID = ID;
        this.fields = fields;
    }
    public AST_class(String ID, List<AST_dec> fields, String name2) {
        /******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
        /* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        //System.out.format("====================== var -> var DOT ID( %s )\n", ID);

        /*******************************/
        /* COPY INPUT DATA NENBERS ... */
        /*******************************/
        this.ID = ID;
        this.fields = fields;
        this.name2 = name2;
    }


    /*************************************************/
    /* The printing message for a field var AST node */

    /*************************************************/
    public void PrintMe() {
        /*********************************/
        /* AST NODE TYPE = AST FIELD VAR */
        /*********************************/
        System.out.printf("AST NODE Class ID(%s)\n", ID);

        /***************************************/
        /* PRINT Node to AST GRAPHVIZ DOT file */
        /***************************************/
        if(name2 == null)
            AST_GRAPHVIZ.getInstance().logNode(
                SerialNumber,
                String.format("class\nID\n...->%s", ID));
        else
            AST_GRAPHVIZ.getInstance().logNode(
                    SerialNumber,
                    String.format("class\nID\nextends\n%s...->%s", name2, ID));

        /**********************************************/
        /* RECURSIVELY PRINT VAR, then FIELD NAME ... */
        /**********************************************/
        for (AST_dec field :
                fields) {
            System.out.print("AST NODE FIELD \n");
            field.PrintMe();



            /****************************************/
            /* PRINT Edges to AST GRAPHVIZ DOT file */
            /****************************************/
            AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, field.getSerialNumber());
        }
    }
}
