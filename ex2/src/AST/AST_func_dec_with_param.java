package AST;


public class AST_func_dec_with_param extends AST_func_dec {
	public AST_type returnType;
	public String funcName;
	public AST_func_params parameters;
	public AST_Program body;
	
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_func_dec_with_param(AST_type returnType, String funcName, AST_func_params parameters, AST_Program body)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("====================== funcDec -> type ID LPAREN funcParam RPAREN LBRACE stmt stmtList RBRACE \n");

		/*******************************/
		/* COPY INPUT DATA MEMBERS ... */
		/*******************************/
		this.returnType = returnType;
		this.funcName = funcName;
		this.parameters = parameters;
		this.body = body;
		
	}
	
	/*************************************************/
	/* The printing message for this AST node */
	/*************************************************/
	public void PrintMe()
	{

		/*************************************/
		/* AST NODE TYPE = AST BINOP EXP */
		/*************************************/
		System.out.print("AST NODE FUNC DEC WITH PARAMETERS\n");

		/**************************************/
		/* RECURSIVLY PRINT */
		/**************************************/
		
		if (returnType != null) returnType.PrintMe();
		if (parameters != null) parameters.PrintMe();
		if (body != null) body.PrintMe();
		
		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("NAME(%s)",funcName));
		
		/****************************************/
		/* PRINT returnType & body to AST GRAPHVIZ DOT file */
		/****************************************/
		if (returnType  != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,returnType.SerialNumber);
		if (parameters  != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,parameters.SerialNumber);
		if (body != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,body.SerialNumber);
	}
}


