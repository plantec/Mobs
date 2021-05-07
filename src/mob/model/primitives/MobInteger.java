package mob.model.primitives;

import mob.ast.MobAstVisitor;

public class MobInteger extends MobPrimitive<Integer> {

	public MobInteger(MobObjectClass def, Integer mob) {
		super(def, mob);
	}

	@Override
	public void accept(MobAstVisitor visitor) {
		visitor.visitInteger(this);
	}

}
