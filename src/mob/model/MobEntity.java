package mob.model;

import mob.sinterpreter.MobContext;

public abstract class MobEntity {
	private MobEntity parent;
	
	public MobEntity parent() {
		return this.parent;
	}

	public void setParent(MobEntity parent) {
		this.parent = parent;
	}

	public Boolean is(Object o) {
		return false;
	}
	
	public String mobString() {
		return this.toString();
	}
	
	public abstract void accept(MobVisitor visitor);
	
	public void run (MobContext ctx, String signature) {}

	
}
