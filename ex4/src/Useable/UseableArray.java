package Useable;

import IR.IR_Code;

import java.util.List;

public class UseableArray extends Useable {

    UseableClass type;
    IR_Code constaractor;

    public UseableArray(String name, UseableClass type, IR_Code constartor)
    {
        super(name);
        this.type = type;
        this.constaractor = constartor;
    }

    public UseableArray(String name, UseableClass type)
    {
        this(name, type, new IR_Code()); // TODO: write the constractor code as loop over the class constactor.
    }
}
