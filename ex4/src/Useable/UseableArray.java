package Useable;

import IR.IR_Code;

import java.util.List;

public class UseableArray extends UseableClass { // this is type of class, all the difference is in constractor, and acsses.

    UseableClass type;

    public UseableArray(String name, UseableClass type, IR_Code constartor)
    {
        super(name, null, constartor);
        this.type = type;
    }

    public UseableArray(String name, UseableClass type)
    {
        this(name, type, new IR_Code()); // TODO: write the constractor code as loop over the class constactor.
    }
}
