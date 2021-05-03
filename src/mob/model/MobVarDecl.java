package mob.model;

public class MobVarDecl extends MobEntity {

	String name;
	MobEntity initialValue;
	
	public void setName(String name) {
		this.name = name;
	}
	public void setInitialValue(MobEntity mobEntity) {
		this.initialValue = mobEntity;
	}
	
	public String name() {
		return this.name;
	}
	public MobEntity initialValue() {
		return this.initialValue;
	}

	@Override
	public void accept(MobVisitor visitor) {
		visitor.visitVarDecl(this);
	}

}
