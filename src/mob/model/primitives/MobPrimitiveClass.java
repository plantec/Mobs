package mob.model.primitives;

public abstract class MobPrimitiveClass<T> extends MobObjectClass {

	
	public MobPrimitiveClass(MobObjectClass def) {
		super(def);
	}

	@Override
	public MobObject newInstance() {
		throw new Error("Should not be sent");
	}
	
	public abstract MobObject newInstance(T mob);
	
}
