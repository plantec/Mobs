package mob.ast;

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
	public void accept(MobInterpretableVisitor visitor) {
		visitor.visitQuoted(this);
	}

}
