package AST;

import java.util.Collections;
import java.util.List;

import Printer.Printer;
import TEMP.*;
import TYPES.*;
import SYMBOL_TABLE.SYMBOL_TABLE;
import IR.*;
import Useable.UseableFunc;

public class AST_func_dec extends AST_dec{
    public List<AST_type> paramList;
    public AST_type type;
    public String name;
    public List<AST_dec> commands;
    public int line;


    @Override
    public TEMP PrintCode(){
       IR_Code.startFunc();
       TEMP ret = TEMP_FACTORY.getInstance().getFreshTEMP();
       IR_Code.currentReturnRegister = ret;
       String label = IRcommand.getFreshLabel(name);
       IR_Code.getInstance().addLine(new IRcommand_Label(label));
       for(AST_dec command: commands)
           command.PrintCode();
       IR_Code funcCode = IR_Code.endFunc();
       UseableFunc func = new UseableFunc(name, label, ret);
       IR_Code.addFunc(funcCode, func);
       return null;
    }


    public AST_func_dec(AST_type type, String name, List<AST_dec> list, int line)
    {
        SerialNumber = AST_Node_Serial_Number.getFresh();
        this.type = type;
        this.name = name;
        this.commands = list;
        this.line = line;
    }
    public AST_func_dec(AST_type type, String name, List<AST_dec> list, List<AST_type> paramList, int line)
    {
        this(type, name, list,line);
        this.paramList = paramList;
    }

    @Override
    public void PrintMe() {
        AST_GRAPHVIZ.getInstance().logNode(SerialNumber,
                String.format("func(%s)\nReturn, Params, Commands", this.name));
        type.PrintMe();
        AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, type.getSerialNumber());
        if(paramList != null)
            for (AST_type param : paramList) {
                param.PrintMe();
                AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, param.getSerialNumber());
            }
        if(commands != null)
            for (AST_dec command :
                    commands) {
                command.PrintMe();
                AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, command.getSerialNumber());
            }

    }

    public TYPE SemantMe()
    {
        TYPE t;
        TYPE_FUNCTION second;
        TYPE_LIST sParams;
        TYPE returnType = null;
        TYPE_LIST type_list = null;
        System.out.println("Semant a func!");
        /*******************/
        /* [0] return type */
        /*******************/
        returnType = SYMBOL_TABLE.getInstance().find(type.type);
        if (returnType == null)
        {
            System.out.format(">> ERROR [%d:%d] non existing return type %s\n",6,6,returnType);
            Printer.printError(line);
        }

        if (SYMBOL_TABLE.getInstance().findInScope(name) != null)
        {
            System.out.format(">> ERROR [%d:%d] func %s already exists in scope\n",2,2,name);
            Printer.printError(line);
        }
        else
        {
            if(SYMBOL_TABLE.getInstance().find(name) != null)
            {
                second = (TYPE_FUNCTION)SYMBOL_TABLE.getInstance().find(name);
                sParams = second.params;
                if (!second.returnType.name.equals(type.type))
                {
                    System.out.format(">> ERROR [%d:%d] func %s already exists out of scope\n",2,2,name);
                    Printer.printError(line);
                }
                for(AST_type param :paramList)
                {
                    if(!param.type.equals(sParams.head.name))
                    {
                        System.out.format(">> ERROR [%d:%d] func %s already exists out of scope\n",2,2,name);
                        Printer.printError(line);
                    }
                    sParams = sParams.tail;
                }
            }

        }



        /****************************/
        /* [1] Begin Function Scope */
        /****************************/
        SYMBOL_TABLE.getInstance().beginScope(name);

        /***************************/
        /* [2] Semant Input Params */
        /***************************/
        if(paramList != null) {
            Collections.reverse(paramList);
            for (AST_type curr : paramList) {
                t = SYMBOL_TABLE.getInstance().find(curr.type);
                if (t == null) {
                    System.out.format(">> ERROR [%d:%d] non existing type %s\n", 2, 2, curr.type);
                    Printer.printError(line);
                } else {
                    type_list = new TYPE_LIST(t, type_list);
                    SYMBOL_TABLE.getInstance().enter(curr.name, t);
                }
            }
        }
//        for (List<AST_type> it = paramList; it  != null; it = it.tail)
//        {
//            t = SYMBOL_TABLE.getInstance().find(it.head.type);
//            if (t == null)
//            {
//                System.out.format(">> ERROR [%d:%d] non existing type %s\n",2,2,it.head.type);
//            }
//            else
//            {
//                type_list = new TYPE_LIST(t,type_list);
//                SYMBOL_TABLE.getInstance().enter(it.head.name,t);
//            }
//        }

        /*******************/
        /* [3] Semant Body */
        /*******************/
        Collections.reverse(commands);
        for (AST_dec command: commands) {
            command.SemantMe();
        }


        /*****************/
        /* [4] End Scope */
        /*****************/
        SYMBOL_TABLE.getInstance().endScope();

        /***************************************************/
        /* [5] Enter the Function Type to the Symbol Table */
        /***************************************************/
        SYMBOL_TABLE.getInstance().enter(name,new TYPE_FUNCTION(returnType,name,type_list));

        /*********************************************************/
        /* [6] Return value is irrelevant for class declarations */
        /*********************************************************/
        return new TYPE_FUNCTION(returnType,name,type_list);
    }
}
