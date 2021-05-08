package mob.model.primitives;

public class MobNilClass extends MobClass {
	private static MobNilClass nilDef = new MobNilClass(null);
	private static MobNil nil = new MobNil(nilDef);
	
	public MobNilClass(MobClass def) {
		super(def);
	}

	@Override
	public MobNil newInstance() {
		return MobNilClass.nil;
	}
}
