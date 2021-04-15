package mob.model.primitives;

import mob.model.MobEntityDef;
import mob.model.MobVisitor;

public class MobFloat extends MobPrimitive<Float> {

	public MobFloat(MobEntityDef def, Float mob) {
		super(def, mob);
	}

	@Override
	public void accept(MobVisitor visitor) {
		visitor.visitFloat(this);
		
	}

}
