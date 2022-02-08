package Useable;

import IR.IR_Code;

import java.util.List;

public class UseableClass extends Useable {

    List<Useable> fields;
    IR_Code constaractor;

    public UseableClass(String name, List<Useable> fields, IR_Code constartor)
    {
        super(name);
        this.fields = fields;
        this.constaractor = constartor;
    }
}
