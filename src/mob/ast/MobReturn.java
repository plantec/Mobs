package mob.ast;

import mob.model.MobEntity;

public class MobReturn extends MobEntity implements MobAstElement {
	MobAstElement returned;
		
	public MobAstElement returned() {
		return returned;
	}

	public void setReturned(MobAstElement returned) {
		this.returned = returned;
	}

	@Override
	public void accept(MobInterpretableVisitor visitor) {
		visitor.visitReturn(this);
	}

}
