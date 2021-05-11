package mob.model.primitives;

import mob.sinterpreter.MobEnvironment;

public class MobStringClass extends MobPrimitiveClass<String> {


	public MobStringClass(String name, MobClass superclass, MobEnvironment environment, MobClass def) {
		super(name, superclass, environment, def);
	}

	@Override
	public MobString newInstance(String mob) {
		return new MobString(this, mob);
	}
}
