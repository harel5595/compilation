package AST;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import Printer.Printer;
import SYMBOL_TABLE.SYMBOL_TABLE;
import TYPES.*;

import IR.*;
import TEMP.*;
import Useable.*;

public class AST_class extends AST_dec {
    /*****/
    /*  var := exp */
    /*****/
    public String ID;
    public List<AST_dec> fields;
    public String name2;
    TYPE_CLASS thisClass;

    /*******/
    /*  CONSTRUCTOR(S) */

    /*******/

    public TEMP PrintCode() {
        IR_Code.startFunc();
        IR_Code.getInstance().addLine(new IRcommand_Label("allocate_"+ID));

        UseableClass my_class;
        List<Useable> inner_fields = new LinkedList<Useable>();
        for(AST_dec field : fields)
        {
            if(((AST_dec_Node)field).head instanceof AST_func_dec)
            {

                UseableFunc func = ((AST_func_dec)((AST_dec_Node)field).head).PrintCode(true);
                inner_fields.add(func);
                //IR_Code.toUse.push(new UseableFunc(ID, "allocate_"+ ID, TEMP_FACTORY.getInstance().getFreshTEMP()));
            }
            else if(((AST_dec_Node)field).head instanceof AST_VAR_dec)
            {
                field.PrintCode(); // allocate the place for the field.
                for(UseableClass c : IR_Code.classes)
                    if(Objects.equals(c.name, ((AST_VAR_dec) ((AST_dec_Node)field).head).type.type)) {
                        inner_fields.add(new UseableVar(field.getName(), c)); // TODO: maybe add more params for the var
                        break;
                    }
            }
            else
            {
                throw new Error("wasn't ready for that type in class");
            }

        }
        IR_Code constractor = IR_Code.endFunc();


        if(thisClass.father != null)
        {
            my_class = new UseableClass(ID,inner_fields, IR_Code.findUseableClass(thisClass.father.name));
        }
        else
        {
            my_class = new UseableClass(ID,inner_fields);
        }
        IR_Code.classes.add(my_class);
        //IR_Code.addFunc(constractor, new UseableFunc("constractor_"+ID,"allocate_"+ID,TEMP_FACTORY.getInstance().getFreshTEMP(), constractor));
        //new UseableClass(ID, )


        return null;
    }



    public AST_class(String ID, List<AST_dec> fields, int line) {
        /**********/
        /* SET A UNIQUE SERIAL NUMBER */
        /**********/
        SerialNumber = AST_Node_Serial_Number.getFresh();
        this.line = line;
        /*************/
        /* PRINT CORRESPONDING DERIVATION RULE */
        /*************/
        System.out.format("====================== var -> var DOT ID( %s )\n", ID);

        /***********/
        /* COPY INPUT DATA NENBERS ... */
        /***********/
        this.ID = ID;
        this.fields = fields;
    }
    public AST_class(String ID, List<AST_dec> fields, String name2, int line) {
        /**********/
        /* SET A UNIQUE SERIAL NUMBER */
        /**********/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /*************/
        /* PRINT CORRESPONDING DERIVATION RULE */
        /*************/
        //System.out.format("====================== var -> var DOT ID( %s )\n", ID);

        /***********/
        /* COPY INPUT DATA NENBERS ... */
        /***********/
        this.ID = ID;
        this.fields = fields;
        this.name2 = name2;
        this.line = line;
    }


    /*****************/
    /* The printing message for a field var AST node */

    /*****************/
    public void PrintMe() {
        /***********/
        /* AST NODE TYPE = AST FIELD VAR */
        /***********/
        System.out.printf("AST NODE Class ID(%s)\n", ID);

        /*************/
        /* PRINT Node to AST GRAPHVIZ DOT file */
        /*************/
        if(name2 == null)
            AST_GRAPHVIZ.getInstance().logNode(
                    SerialNumber,
                    String.format("class\nID\n...->%s", ID));
        else
            AST_GRAPHVIZ.getInstance().logNode(
                    SerialNumber,
                    String.format("class\nID\nextends\n%s...->%s", name2, ID));

        /****************/
        /* RECURSIVELY PRINT VAR, then FIELD NAME ... */
        /****************/
        for (AST_dec field :
                fields) {
            System.out.print("AST NODE FIELD \n");
            field.PrintMe();



            /**************/
            /* PRINT Edges to AST GRAPHVIZ DOT file */
            /**************/
            AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, field.getSerialNumber());
        }
    }

    public TYPE SemantMe()
    {
        if(!SYMBOL_TABLE.getInstance().isScopeGlobal())
        {
            System.out.format(">> ERROR [%d:%d] class%s local scope\n",2,2,ID);
            Printer.printError(line);
        }
        if (SYMBOL_TABLE.getInstance().find_in_class(ID, name2) != null)
        {
            System.out.format(">> ERROR [%d:%d] class %s already exists in scope\n",2,2,ID);
            Printer.printError(line);
        }
        /*********/
        /* [1] Begin Class Scope */
        /*********/


        /*********/
        /* [2] Semant Data Members */
        /*********/

        TYPE_CLASS father = null;
        if(name2 != null) {
            father = (TYPE_CLASS) SYMBOL_TABLE.getInstance().find(name2);
            if (father == null) {
                System.out.format("ERROR: the father %s of the class %s don't exist.", name2, ID);
                Printer.printError(line);
                return null;
            }
        }
        TYPE_CLASS t = new TYPE_CLASS(father,ID,null,null);
        thisClass = t;
        SYMBOL_TABLE.getInstance().enter(ID,t);
        /*******/
        /* [3] End Scope */
        /*******/
        SYMBOL_TABLE.getInstance().beginScope(ID);
        TYPE_LIST l = null;
        LinkedList<String> names = new LinkedList<String>();
        Collections.reverse(fields);
        for(AST_dec field : fields) {

            l = new TYPE_LIST(field.SemantMe(), l);

            names.add(field.getName());
        }
        SYMBOL_TABLE.getInstance().endScope();
        Collections.reverse(names);
        t.data_members = l;
        t.data_names = names;
        /****************/
        /* [4] Enter the Class Type to the Symbol Table */
        /****************/


        /*******************/
        /* [5] Return value is irrelevant for class declarations */
        /*******************/
        return null;
    }
}