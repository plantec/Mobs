package mob.model;

public class MobVarDecl extends MobEntity {

	String name;
	MobObject initialValue;
	
	public void setName(String name) {
		this.name = name;
	}
	public void setInitialValue(MobObject mobExp) {
		this.initialValue = mobExp;
	}
	
	public String name() {
		return this.name;
	}
	public MobObject initialValue() {
		return this.initialValue;
	}

	@Override
	public void accept(MobVisitor visitor) {
		visitor.visitVarDecl(this);
	}

}
