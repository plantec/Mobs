package mob.model.primitives;

import mob.sinterpreter.MobEnvironment;

public abstract class MobPrimitiveClass<T> extends MobMetaClass {

	public MobPrimitiveClass(String name, MobClass superclass, MobEnvironment environment, MobClass def) {
		super(name, superclass, environment, def);
	}

	@Override
	public MobObject newInstance() {
		throw new Error("Should not be sent");
	}
	
	public abstract MobObject newInstance(T mob);
	
}
