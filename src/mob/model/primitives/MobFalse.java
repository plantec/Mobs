package mob.model.primitives;

import mob.model.MobObjectDef;
import mob.model.MobVisitor;

public class MobFalse extends MobBool {

	public MobFalse(MobObjectDef def) {
		super(def, false);
	}

	@Override
	public void accept(MobVisitor visitor) {
		visitor.visitFalse(this);
		
	}
	
	

}
