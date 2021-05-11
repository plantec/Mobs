package mob.model.primitives;

import mob.sinterpreter.MobEnvironment;

public class MobObjectClass extends MobMetaClass {

	public MobObjectClass(String name, MobClass superclass, MobEnvironment environment, MobClass def) {
		super(name, superclass, environment, def);
	}

	@Override
	public MobObject newInstance() {
		return new MobObject(this);
	}

}
