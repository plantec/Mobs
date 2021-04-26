package mob.model.primitives;

import mob.model.MobObjectDef;
import mob.model.MobVisitor;

public class MobTrue extends MobBool {

	public MobTrue(MobObjectDef def) {
		super(def, true);
	}

	@Override
	public void accept(MobVisitor visitor) {
			visitor.visitTrue(this);
	}
}
