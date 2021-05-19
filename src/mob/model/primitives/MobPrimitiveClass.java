package mob.model.primitives;

import mob.model.MobClass;
import mob.model.MobObject;
import mob.model.MobObjectClass;
import mob.sinterpreter.MobEnvironment;

public abstract class MobPrimitiveClass<T> extends MobObjectClass {

	public MobPrimitiveClass(String name, MobClass superclass, MobEnvironment environment, MobClass def) {
		super(name, superclass, environment, def);
	}
	
	protected void initializePrimitives() {
		super.initializePrimitives();
	}
	
	@Override
	public MobObject newInstance() {
		throw new Error("Should not be sent");
	}
	
	public abstract MobObject newInstance(T mob);
	
}
