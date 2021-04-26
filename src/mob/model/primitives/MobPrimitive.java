package mob.model.primitives;

import mob.model.MobObject;
import mob.model.MobObjectDef;

public abstract class MobPrimitive<T> extends MobObject {

	private T rawValue;
	
	public MobPrimitive(MobObjectDef def, T value) {
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
