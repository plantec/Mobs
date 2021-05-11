package mob.ast;

import mob.model.MobEntity;

public class MobVarDecl extends MobEntity implements MobAstElement {
	String name;
	MobAstElement initialValue;
	
	public void setName(String name) {
		this.name = name;
	}
	public void setInitialValue(MobAstElement mobEntity) {
		this.initialValue = mobEntity;
	}
	
	public String name() {
		return this.name;
	}
	public MobAstElement initialValue() {
		return this.initialValue;
	}

	@Override
	public void accept(MobInterpretableVisitor visitor) {
		visitor.visitVarDecl(this);
	}

}
