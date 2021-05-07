package mob.model.primitives;

import mob.ast.MobAstElement;

public abstract class MobPrimitive<T> extends MobObject implements MobAstElement {

	private T rawValue;
	
	public MobPrimitive(MobObjectClass def, T value) {
		super(def);
		this.rawValue = value;
	}
			
	public T rawValue() {
		return this.rawValue;
	}
	
	public Boolean is (Object o) {
		return o.equals(rawValue);
	}
	
	public String toString() {
		return this.rawValue().toString();
	}

}
