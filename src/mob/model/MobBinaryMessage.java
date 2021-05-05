package mob.model;

public class MobBinaryMessage extends MobMessageSend {
	String operator;
	MobEntity argument;
	
	
	public String operator() {
		return this.operator;
	}


	public void setOperator(String operator) {
		this.operator = operator;
	}


	public MobEntity argument() {
		return this.argument;
	}


	public void setArgument(MobEntity argument) {
		this.argument = argument;
		this.argument.setParent(this);
	}


	@Override
	public void accept(MobVisitor visitor) {
		visitor.visitBinaryMessageSend(this);
	}

}
