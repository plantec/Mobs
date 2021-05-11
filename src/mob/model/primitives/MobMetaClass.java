package mob.model.primitives;

import mob.sinterpreter.MobEnvironment;

public class MobMetaClass extends MobClass {

	public MobMetaClass(String name, MobClass superclass, MobEnvironment environment, MobClass def) {
		super(name, superclass, environment, def);
	}

	protected void initializePrimitives() {
		super.initializePrimitives();
	}
	

}
