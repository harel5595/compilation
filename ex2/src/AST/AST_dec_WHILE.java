package AST;

public class AST_dec_WHILE extends AST_dec
{
	public AST_EXP cond;
	public AST_Program body;

	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_dec_WHILE(AST_EXP cond, AST_Program body)
	{
		this.cond = cond;
		this.body = body;
	}
}