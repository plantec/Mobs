package mob.model.primitives;

import mob.model.MobObject;
import mob.model.MobObjectDef;
import mob.model.MobVisitor;

public class MobNil extends MobObject {

	public MobNil(MobObjectDef def) {
		super(def);
	}

	@Override
	public void accept(MobVisitor visitor) {
		visitor.visitNil(this);
		
	}

}
