package IR;

enum IR_Types{
    LABEL,
    ASSINGNMENT,
    PLUS,
    MINUS,
    BRANCH,
    CALL,
    MUL,
    RETURN,
    ARRAY_SET,
    FIELD_SET,
    VIRTUAL_CALL


}

public class IR_Line {
    public int type = 0;
    public String label = null;
    public String assignment = null;
    public String op = null;
    public String left = null;
    public String right = null;
    public String extra = null;
}
