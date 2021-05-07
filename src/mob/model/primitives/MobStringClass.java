package mob.model.primitives;

public class MobStringClass extends MobPrimitiveClass<String> {

	public MobStringClass(MobObjectClass def) {
		super(def);
	}

	@Override
	public MobString newInstance(String mob) {
		return new MobString(this, mob);
	}
}
