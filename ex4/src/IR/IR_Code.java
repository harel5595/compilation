package IR;

import java.util.List;

public class IR_Code {
    public List<IR_Line> code;
    public IR_Code()
    {
     code = null;
    }
    public List<IR_Line> getCode() {
        return code;
    }
    /*
    add the code in the param to the end of the code that in this class.
     */
    public IR_Code addCode(IR_Code to_add){
        code.addAll(to_add.getCode());
        return this;
    }

    static private int curr = 0;
    static public String getNewRegisterName()
    {
        return "t" + curr++;
    }

    public IR_Code addLine(IR_Line line)
    {
        code.add(line);
        return this;
    }

}
