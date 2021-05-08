package mob.model.primitives;

import mob.sinterpreter.MobEnvironment;

public class MobCharacterClass extends MobPrimitiveClass<Character> {

	public MobCharacterClass(MobEnvironment environment, MobClass def) {
		super(environment, def);
	}

	@Override
	public MobCharacter newInstance(Character mob) {
		return new MobCharacter(this, mob);
	}

}
