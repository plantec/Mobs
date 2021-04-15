package mob.model.primitives;

import mob.model.MobEntityDef;
import mob.model.MobVisitor;

public class MobTrue extends MobBool {

	public MobTrue(MobEntityDef def) {
		super(def, true);
	}

	@Override
	public void accept(MobVisitor visitor) {
			visitor.visitTrue(this);
	}
}
