package mob.model.primitives;

import mob.ast.MobAstVisitor;

public class MobTrue extends MobBoolean {

	public MobTrue(MobClass def) {
		super(def, true);
	}

	@Override
	public void accept(MobAstVisitor visitor) {
			visitor.visitTrue(this);
	}
}
