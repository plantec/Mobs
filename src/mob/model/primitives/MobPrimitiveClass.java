package mob.model.primitives;

import mob.sinterpreter.MobEnvironment;

public abstract class MobPrimitiveClass<T> extends MobClass {

	public MobPrimitiveClass(MobEnvironment environment, MobClass def) {
		super(environment, def);
	}

	@Override
	public MobObject newInstance() {
		throw new Error("Should not be sent");
	}
	
	public abstract MobObject newInstance(T mob);
	
}
