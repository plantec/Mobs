package mob.model.primitives;

import mob.model.MobEntityDef;
import mob.model.MobVisitor;

public class MobString extends MobPrimitive<String> {

	public MobString(MobEntityDef def, String mob) {
		super(def, mob);
	}

	@Override
	public void accept(MobVisitor visitor) {
		visitor.visitString(this);
		
	}

}
