package mob.model;

import mob.sinterpreter.MobEnvironment;

public class MobObjectClass extends MobClass {

	public MobObjectClass(String name,MobEnvironment environment,  MobClass superclass, MobClass def) {
		super(name, environment, superclass, def);
	}
}
