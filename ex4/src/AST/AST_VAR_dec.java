package AST;

import Printer.*;
import TEMP.TEMP;
import TYPES.*;
import SYMBOL_TABLE.SYMBOL_TABLE;
import IR.*;

import java.util.Objects;
import TEMP.*;
import Useable.UseableClass;
import Useable.UseableVar;

public class AST_VAR_dec extends AST_dec{
    public AST_type type;
    public String name;
    public AST_Node exp;
    public int line;

    public String getName() {
        return name;
    }

    public TEMP PrintCode()
    {

        IR_Code.getInstance().addLine(new IRcommand_Allocate(name)); // TODO: change the func to allocate by the type
        // TODO: search for type in classes and arrays.
        UseableVar thisVar = null;
        for(UseableClass c : IR_Code.classes)
            if(Objects.equals(c.name, type.type))
            {
                thisVar = new UseableVar(name, c);
                break;
            }

        if(thisVar == null)
            throw new Error("dont know this type.");

        IR_Code.addVar(thisVar);

        if (exp != null)
        {
            IR_Code.getInstance().addLine(new IRcommand_Store(name,exp.PrintCode()));
        }
        return null;
    }


    public AST_VAR_dec(AST_type type , String name, int line)
    {
        this.type = type;
        this.name = name;
        this.line = line;
        SerialNumber  = AST_Node_Serial_Number.getFresh();
    }

    public AST_VAR_dec(AST_type type ,String name, AST_Node exp, int line)
    {
        this(type, name, line);
        this.exp = exp;
    }

    public void PrintMe()
    {
        /********************************************/
        /* AST NODE TYPE = AST ASSIGNMENT STATEMENT */
        /********************************************/
        System.out.print("AST NODE ASSIGN STMT\n");

        /***********************************/
        /* RECURSIVELY PRINT VAR + EXP ... */
        /***********************************/
        if (type != null) type.PrintMe();
        if (exp != null) exp.PrintMe();

        /***************************************/
        /* PRINT Node to AST GRAPHVIZ DOT file */
        /***************************************/
        AST_GRAPHVIZ.getInstance().logNode(
                SerialNumber,
                "ASSIGN\nleft := right\n");

        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        if(type != null)
            AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,type.getSerialNumber());
        if(exp != null)
            AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,exp.getSerialNumber());
    }

    public TYPE SemantMe()
    {
        TYPE t;

        /****************************/
        /* [1] Check If Type exists */
        /****************************/
        t = SYMBOL_TABLE.getInstance().find(type.type);
        if (t == null || t instanceof TYPE_VOID)
        {
            System.out.format(">> ERROR [%d:%d] non existing type %s\n",2,2,type);
            Printer.printError(line);
            return null;
        }

        /**************************************/
        /* [2] Check That Name does NOT exist */
        /**************************************/
        if (SYMBOL_TABLE.getInstance().findInScope(name) != null)
        {
            System.out.format(">> ERROR [%d:%d] variable %s already exists in scope\n",2,2,name);
            Printer.printError(line);
        }
        else
        {
            if(SYMBOL_TABLE.getInstance().find(name)!=null)
            {
                if(SYMBOL_TABLE.getInstance().find(name) != t)
                {
                    System.out.format(">> ERROR [%d:%d] %s is defined with a different type elsewhere\n",2,2,name);
                    Printer.printError(line);
                }
            }
        }

        /****************************************/
        /* [2] Check If The EXP is the Same Type*/
        /****************************************/
        if(exp != null)
        {
            TYPE expType = exp.SemantMe();
            if(expType instanceof TYPE_CLASS) {
                while (!Objects.equals(t.name, expType.name) && ((TYPE_CLASS) expType).father != null) {
                    expType = ((TYPE_CLASS) expType).father;
                }
            }
            if(!Objects.equals(t.name, expType.name))
            {
                if(expType instanceof TYPE_ARRAY && t instanceof TYPE_ARRAY && Objects.equals(((TYPE_ARRAY) expType).elementsType.name, ((TYPE_ARRAY) t).elementsType.name) && Objects.equals(expType.name, "SUPER-DUPER"))
                {

                }
                else {
                    System.out.format(">> ERROR [%d:%d] mismatching assignment types. %s, %s\n", 2, 2, t.name, expType.name);
                    Printer.printError(line);
                }
            }
        }
        /***************************************************/
        /* [3] Enter the Function Type to the Symbol Table */
        /***************************************************/
        SYMBOL_TABLE.getInstance().enter(name,t);

        /*********************************************************/
        /* [4] Return value is irrelevant for class declarations */
        /*********************************************************/
        return t;
    }

}
