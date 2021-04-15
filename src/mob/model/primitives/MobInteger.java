package mob.model.primitives;

import mob.model.MobEntityDef;
import mob.model.MobVisitor;

public class MobInteger extends MobPrimitive<Integer> {

	public MobInteger(MobEntityDef def, Integer mob) {
		super(def, mob);
	}

	@Override
	public void accept(MobVisitor visitor) {
		visitor.visitInteger(this);
	}

}
