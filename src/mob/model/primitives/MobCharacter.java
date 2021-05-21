package mob.model.primitives;

import mob.model.MobClass;
import mob.model.MobObjectClass;
import mob.sinterpreter.MobEnvironment;

public class MobCharacter extends MobObjectClass {

	public MobCharacter(String name, MobEnvironment environment, MobClass superclass, MobClass def) {
		super(name, environment, superclass, def);
	}

}
