package mob.model.primitives;


public class MobCharacterClass extends MobPrimitiveClass<Character> {

	public MobCharacterClass(MobClass def) {
		super(def);
	}

	@Override
	public MobCharacter newInstance(Character mob) {
		return new MobCharacter(this, mob);
	}

}
