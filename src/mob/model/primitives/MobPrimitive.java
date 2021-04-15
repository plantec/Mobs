package mob.model.primitives;

import mob.model.MobEntity;
import mob.model.MobEntityDef;

public abstract class MobPrimitive<T> extends MobEntity {

	private T rawValue;
	
	public MobPrimitive(MobEntityDef def, T value) {
		super(def);
		this.rawValue = value;
	}
			
	public T rawValue() {
		return this.rawValue;
	}
	
	public Boolean is (Object o) {
		return o.equals(rawValue);
	}
	
}
