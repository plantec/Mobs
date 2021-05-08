package mob.model.primitives;

import mob.sinterpreter.MobEnvironment;

public class MobObjectClass extends MobClass {

	public MobObjectClass(MobEnvironment environment, MobClass def) {
		super(environment, def);
	}

	@Override
	public MobObject newInstance() {
		return new MobObject(this);
	}

}
