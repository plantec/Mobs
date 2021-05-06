package mob.model;

public class MobReturn extends MobEntity {
	MobEntity returned;
		
	public MobEntity returned() {
		return returned;
	}

	public void setReturned(MobEntity returned) {
		this.returned = returned;
		this.returned.setParent(this);
	}

	@Override
	public void accept(MobVisitor visitor) {
		visitor.visitReturn(this);
	}

}
