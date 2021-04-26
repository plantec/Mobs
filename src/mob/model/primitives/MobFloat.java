package mob.model.primitives;

import mob.model.MobObjectDef;
import mob.model.MobVisitor;

public class MobFloat extends MobPrimitive<Float> {

	public MobFloat(MobObjectDef def, Float mob) {
		super(def, mob);
	}

	@Override
	public void accept(MobVisitor visitor) {
		visitor.visitFloat(this);
		
	}

}
