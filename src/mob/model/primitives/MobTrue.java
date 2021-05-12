package mob.model.primitives;

import mob.ast.MobInterpretableVisitor;
import mob.model.MobClass;

public class MobTrue extends MobBoolean {

	public MobTrue(MobClass def) {
		super(def, true);
	}

	@Override
	public void accept(MobInterpretableVisitor visitor) {
			visitor.visitTrue(this);
	}
}
