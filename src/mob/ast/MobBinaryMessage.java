package mob.ast;


public class MobBinaryMessage extends MobMessage {
	protected String operator;
	protected MobAstElement argument;

	public String operator() {
		return this.operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public MobAstElement argument() {
		return this.argument;
	}

	public void setArgument(MobAstElement argument) {
		this.argument = argument;
	}

	@Override
	public void accept(MobInterpretableVisitor visitor) {
		visitor.visitBinaryMessage(this);
	}

}
