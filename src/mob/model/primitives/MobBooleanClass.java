package mob.model.primitives;

import mob.model.MobClass;
import mob.model.MobMetaClass;
import mob.sinterpreter.MobEnvironment;

public abstract class MobBooleanClass extends MobMetaClass {

	public MobBooleanClass(String name, MobClass superclass, MobEnvironment environment, MobClass def) {
		super(name, superclass, environment, def);
	}

	
}
