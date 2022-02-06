package IR;

enum IR_Types{
    LABLE,
    ASSIGNMENT

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
