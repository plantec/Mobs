package mob.model.primitives;

import mob.model.MobObject;
import mob.model.MobObjectDef;

public abstract class MobPrimitiveDef<T> extends MobObjectDef {

	@Override
	public MobObject newInstance() {
		throw new Error("Should not be sent");
	}
	
	public abstract MobObject newInstance(T mob);
	
}
