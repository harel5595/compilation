package Useable;

public class UseableVar extends Useable{
    public UseableClass type;
    public int offset = 0; // place on the stack
    // TODO: add info about the var so we could use it
    public UseableVar(String name, UseableClass type)
    {
        super(name);
        this.type = type;
    }
}
