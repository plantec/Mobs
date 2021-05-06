package mob.model;

import mob.sinterpreter.MobContext;

public abstract class MobEntity {
	private MobEntity parent;
	Boolean showParenthesis = true;
	
	public MobEntity parent() {
		return this.parent;
	}
	
	public MobEntity withoutParenthesis() {
		this.showParenthesis = false;
		return this;
	}

	public Boolean showParenthesis() {
		return this.showParenthesis;
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
