package mob.model;

public abstract class MobEntity {

	private int quote;
	private MobEntity parent;
	
	public MobEntity parent() {
		return this.parent;
	}

	public void setParent(MobEntity parent) {
		this.parent = parent;
	}

	public int quote() {
		return this.quote;
	}
	public void setQuote(int q) {
		this.quote = q;
	}
	
	public Boolean is(Object o) {
		return false;
	}
	
	public abstract void accept(MobVisitor visitor);
	
}
