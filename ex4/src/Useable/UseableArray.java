package Useable;

import IR.IR_Code;

import java.util.List;

public class UseableArray extends UseableClass { // this is type of class, all the difference is in constractor, and acsses.

    UseableClass type;

    public UseableArray(String name, UseableClass type) // TODO: write the constractor code as loop over the class constactor.
    {
        super(name, null);
        this.type = type;
    }

}
