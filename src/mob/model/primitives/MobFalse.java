package mob.model.primitives;

import mob.ast.MobAstVisitor;

public class MobFalse extends MobBoolean {

	public MobFalse(MobClass def) {
		super(def, false);
	}

	@Override
	public void accept(MobAstVisitor visitor) {
		visitor.visitFalse(this);	
	}

}
