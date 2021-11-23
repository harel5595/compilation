package AST;

public class AST_dec_IF extends AST_dec
{
	public AST_EXP cond;
	public AST_Program body;

	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_dec_IF(AST_EXP cond, AST_Program body)
	{
		this.cond = cond;
		this.body = body;
	}
}