package mob.model.primitives;

import mob.ast.MobInterpretableVisitor;

public class MobFloat extends MobPrimitive<Float> {

	public MobFloat(MobClass def, Float mob) {
		super(def, mob);
	}

	@Override
	public void accept(MobInterpretableVisitor visitor) {
		visitor.visitFloat(this);		
	}

}
