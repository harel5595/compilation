package TYPES;

public class TYPE_ARRAY extends TYPE{

    public TYPE elementsType;

    public TYPE_ARRAY(String name,TYPE elementsType)
    {
        this.name = name;
        this.elementsType = elementsType;
    }

    @Override
    public boolean isArray() {
        return true;
    }
}
