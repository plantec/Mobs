package mob.model.primitives;

import mob.ast.MobInterpretableVisitor;
import mob.model.MobClass;

public class MobInteger extends MobPrimitive<Integer> {

	public MobInteger(MobClass def, Integer mob) {
		super(def, mob);
	}

	@Override
	public void accept(MobInterpretableVisitor visitor) {
		visitor.visitInteger(this);
	}

}
