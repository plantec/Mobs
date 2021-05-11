package mob.model.primitives;

import mob.sinterpreter.MobEnvironment;

public class MobCharacterClass extends MobPrimitiveClass<Character> {

	public MobCharacterClass(String name, MobClass superclass, MobEnvironment environment, MobClass def) {
		super(name, superclass, environment, def);
	}

	@Override
	public MobCharacter newInstance(Character mob) {
		return new MobCharacter(this, mob);
	}

}
