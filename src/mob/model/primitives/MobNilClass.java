package mob.model.primitives;

import mob.sinterpreter.MobEnvironment;

public class MobNilClass extends MobMetaClass {
	
	public MobNilClass(String name, MobClass superclass, MobEnvironment environment, MobClass def) {
		super(name, superclass, environment, def);
	}

	@Override
	public MobNil newInstance() {
		return new MobNil(this);
	}

}
