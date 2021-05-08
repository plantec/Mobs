package mob.model.primitives;

public class MobStringClass extends MobPrimitiveClass<String> {

	public MobStringClass(MobClass def) {
		super(def);
	}

	@Override
	public MobString newInstance(String mob) {
		return new MobString(this, mob);
	}
}
