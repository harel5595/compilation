package AST;

import java.util.List;

public class AST_dec_WHILE extends AST_dec
{
	public AST_EXP cond;
	public List<AST_dec> body;

	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_dec_WHILE(AST_EXP cond, List<AST_dec> body)
	{
		SerialNumber = AST_Node_Serial_Number.getFresh();

		this.cond = cond;
		this.body = body;
	}

	@Override
	public void PrintMe() {
		AST_GRAPHVIZ.getInstance().logNode(SerialNumber,
				"While\nCond, commands");
		cond.PrintMe();
		AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, cond.getSerialNumber());
		for(AST_dec command: body)
		{
			command.PrintMe();
			AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, command.getSerialNumber());
		}
	}





}