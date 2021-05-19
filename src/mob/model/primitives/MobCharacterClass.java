package mob.model.primitives;

import mob.model.MobClass;
import mob.model.MobObjectClass;
import mob.sinterpreter.MobEnvironment;

public class MobCharacterClass extends MobObjectClass {

	public MobCharacterClass(String name, MobEnvironment environment, MobClass superclass, MobClass def) {
		super(name, environment, superclass, def);
	}

}
