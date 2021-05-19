package mob.model.primitives;

import mob.model.MobClass;
import mob.model.MobObject;
import mob.sinterpreter.MobEnvironment;

public class MobCharacterClass extends MobPrimitiveClass<Character> {

	public MobCharacterClass(String name, MobClass superclass, MobEnvironment environment, MobClass def) {
		super(name, superclass, environment, def);
	}

	@Override
	public MobObject newInstance(Character mob) {
		MobObject i = new MobObject(this);
		i.instVarAtPut(0, mob);
		return i;
	}

}
