package mob.model.primitives;

import mob.model.MobObjectDef;
import mob.model.MobVisitor;

public class MobInteger extends MobPrimitive<Integer> {

	public MobInteger(MobObjectDef def, Integer mob) {
		super(def, mob);
	}

	@Override
	public void accept(MobVisitor visitor) {
		visitor.visitInteger(this);
	}

}
