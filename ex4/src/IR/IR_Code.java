package IR;

import java.util.List;

public class IR_Code {
    static public List<IRcommand> code;
    public IR_Code()
    {
     code = null;
    }
    public List<IRcommand> getCode() {
        return code;
    }
    /*
    add the code in the param to the end of the code that in this class.
     */
    static public void addCode(IR_Code to_add){
        code.addAll(to_add.getCode());
    }

    static private int curr = 0;
    static public String getNewRegisterName()
    {
        return "t" + curr++;
    }

    static public void addLine(IRcommand line)
    {
        code.add(line);
    }

}
