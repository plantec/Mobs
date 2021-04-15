package mob.model;

public abstract class MobProtoEntity implements MobExp {

	private int quote;
	private MobUnit parent;
	
	public MobUnit parent() {
		return this.parent;
	}

	public void setParent(MobUnit parent) {
		this.parent = parent;
	}

	public int quote() {
		return this.quote;
	}
	public void setQuote(int q) {
		this.quote = q;
	}
	
}
