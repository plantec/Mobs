package mob.model.primitives;

public abstract class MobPrimitiveClass<T> extends MobClass {

	
	public MobPrimitiveClass(MobClass def) {
		super(def);
	}

	@Override
	public MobObject newInstance() {
		throw new Error("Should not be sent");
	}
	
	public abstract MobObject newInstance(T mob);
	
}
