package mob.ast;

import mob.model.MobEntity;

public class MobQuoted extends MobEntity implements MobAstElement {
	MobAstElement entity;

	public MobQuoted(MobAstElement entity) {
		this.setEntity(entity);
	}

	public MobAstElement entity() {
		return this.entity;
	}

	public void setEntity(MobAstElement entity) {
		this.entity = entity;
	}

	@Override
	public void accept(MobAstVisitor visitor) {
		visitor.visitQuoted(this);
	}

}