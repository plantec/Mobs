package mob.model.primitives;

import mob.ast.MobAstElement;
import mob.model.MobClass;
import mob.model.MobObject;

public abstract class MobPrimitive<T> extends MobObject implements MobAstElement {

	public MobPrimitive(MobClass def, T value) {
		super(def);
		this.setRawValue(value);
	}
			
	public void setRawValue(T value) {
		this.instVarAtPut(0, value);
	}
	
	@SuppressWarnings("unchecked")
	public T rawValue() {
		return (T) this.instVarAt(0);
	}
	
	public Boolean is (Object o) {
		return o.equals(this.rawValue());
	}
	
	public String toString() {
		return this.rawValue().toString();
	}

}
