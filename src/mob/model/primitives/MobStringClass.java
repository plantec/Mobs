package mob.model.primitives;

import mob.sinterpreter.MobEnvironment;

public class MobStringClass extends MobPrimitiveClass<String> {

	public MobStringClass(MobEnvironment environment, MobClass def) {
		super(environment, def);
	}

	@Override
	public MobString newInstance(String mob) {
		return new MobString(this, mob);
	}
}
