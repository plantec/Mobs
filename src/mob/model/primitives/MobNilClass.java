package mob.model.primitives;

import mob.sinterpreter.MobEnvironment;

public class MobNilClass extends MobClass {
	
	public MobNilClass(MobEnvironment environment, MobClass def) {
		super(environment, def);
	}
	
	@Override
	public MobNil newInstance() {
		return new MobNil(this);
	}

}
