package mob.model.primitives;

import mob.ast.MobInterpretableVisitor;

public class MobInteger extends MobPrimitive<Integer> {

	public MobInteger(MobClass def, Integer mob) {
		super(def, mob);
	}

	@Override
	public void accept(MobInterpretableVisitor visitor) {
		visitor.visitInteger(this);
	}

}
