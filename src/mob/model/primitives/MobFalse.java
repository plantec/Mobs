package mob.model.primitives;

import mob.model.MobEntityDef;
import mob.model.MobVisitor;

public class MobFalse extends MobBool {

	public MobFalse(MobEntityDef def) {
		super(def, false);
	}

	@Override
	public void accept(MobVisitor visitor) {
		visitor.visitFalse(this);
		
	}
	
	

}
