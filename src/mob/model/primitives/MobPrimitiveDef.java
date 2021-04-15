package mob.model.primitives;

import mob.model.MobEntity;
import mob.model.MobEntityDef;

public abstract class MobPrimitiveDef<T> extends MobEntityDef {

	@Override
	public MobEntity newInstance() {
		throw new Error("Should not be sent");
	}
	
	public abstract MobEntity newInstance(T mob);
	
}
