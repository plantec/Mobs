package mob.model;

public class MobQuoted extends MobEntity {

	MobEntity entity;
	
	public MobQuoted(MobEntity entity) {
		this.setEntity(entity);
	}

	public MobEntity entity() {
		return this.entity;
	}

	public void setEntity(MobEntity entity) {
		this.entity = entity;
		this.entity.setParent(this);
	}

	@Override
	public void accept(MobVisitor visitor) {
		visitor.visitQuoted(this);
	}

}
