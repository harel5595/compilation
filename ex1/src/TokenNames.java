public interface TokenNames {
  /* terminals */
  public static final int EOF = 0;
  public static final int PLUS = 1;
  public static final int MINUS = 2;
  public static final int TIMES = 3;
  public static final int DIVIDE = 4;
  public static final int LPAREN = 5;
  public static final int RPAREN = 6;
  public static final int NUMBER = 7;
  public static final int ID = 8;

  public static final int LBRACK = 9;
  public static final int RBRACK = 10;
  public static final int LBRACE = 11;
  public static final int RBRACE = 12;
  public static final int NIL = 13;
  public static final int COMMA = 14;
  public static final int DOT = 15;
  public static final int SEMICOLON = 16;
  public static final int TYPE_INT = 17;
  public static final int ASSIGN = 18;
  public static final int EQ = 19;
  public static final int LT = 20;
  public static final int GT = 21;
  public static final int ARRAY = 22;
  public static final int CLASS = 23;
  public static final int EXTENDS = 24;
  public static final int RETURN = 25;
  public static final int WHILE = 26;
  public static final int IF = 27;
  public static final int NEW = 28;
  public static final int STRING = 29;
  public static final int TYPE_STRING = 30;

  public static String to_name(int type)
  {
    switch (type)
    {
      case 0:
      return "EOF";
      case 1:
        return "PLUS";
      case 2:
        return "MINUS";
      case 3:
        return "TIMES";
      case 4:
        return "DIVIDE";
      case 5:
        return "LPAREN";
      case 6:
        return "RPAREN";
      case 7:
        return "NUMBER";
      case 8:
        return "ID";
      case 9:
        return "LBRACK";
      case 10:
        return "RBRACK";
      case 11:
        return "LBRACE";
      case 12:
        return "RBRACE";
      case 13:
        return "NIL";
      case 14:
        return "COMMA";
      case 15:
        return "DOT";
      case 16:
        return "SEMICOLON";
      case 17:
        return "TYPE_INT";
      case 18:
        return "ASSIGN";
      case 19:
        return "EQ";
      case 20:
        return "LT";
      case 21:
        return "GT";
      case 22:
        return "ARRAY";
      case 23:
        return "CLASS";
      case 24:
        return "EXTENDS";
      case 25:
        return "RETURN";
      case 26:
        return "WHILE";
      case 27:
        return "IF";
      case 28:
        return "NEW";
      case 29:
        return "STRING";
      case 30:
        return "TYPE_STRING";
      default:
        return "ERROR";
    }
  }

}
