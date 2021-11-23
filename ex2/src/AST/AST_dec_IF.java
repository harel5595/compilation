package AST;

import java.util.List;

public class AST_dec_IF extends AST_dec
{
	public AST_EXP cond;
	public List<AST_dec> body;

	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_dec_IF(AST_EXP cond, List<AST_dec> body)
	{
		this.cond = cond;
		this.body = body;
	}
}