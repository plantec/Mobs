package mob.model.primitives;

import mob.model.MobEntityDef;
import mob.model.MobVisitor;

public class MobSymbol extends MobPrimitive<String> {

	public MobSymbol(MobEntityDef def, String mob) {
		super(def, mob);
	}

	@Override
	public void accept(MobVisitor visitor) {
		visitor.visitSymbol(this);
	}

}
