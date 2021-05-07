package mob.ast;

import mob.model.primitives.MobSymbol;

public class MobBinaryMessage extends MobMessage {
	protected MobSymbol operator;
	protected MobAstElement argument;

	public MobSymbol operator() {
		return this.operator;
	}

	public void setOperator(MobSymbol operator) {
		this.operator = operator;
	}

	public MobAstElement argument() {
		return this.argument;
	}

	public void setArgument(MobAstElement argument) {
		this.argument = argument;
	}

	@Override
	public void accept(MobAstVisitor visitor) {
		visitor.visitBinaryMessage(this);
	}

}
