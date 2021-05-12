package mob.model.primitives;

import mob.ast.MobInterpretableVisitor;
import mob.model.MobClass;

public class MobFalse extends MobBoolean {

	public MobFalse(MobClass def) {
		super(def, false);
	}

	@Override
	public void accept(MobInterpretableVisitor visitor) {
		visitor.visitFalse(this);	
	}

}
