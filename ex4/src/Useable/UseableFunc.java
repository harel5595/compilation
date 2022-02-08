package Useable;
import TEMP.*;
public class UseableFunc extends Useable{
    public String startLabel;
    public TEMP retRegister;
    // TODO: add info about the func so we could call it
    public UseableFunc(String name, String startLabel, TEMP ret)
    {
        super(name);
        this.startLabel = startLabel;
        retRegister = ret;
    }
}
