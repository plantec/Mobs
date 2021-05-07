package mob.model.primitives;

public class MobNilClass extends MobObjectClass {
	private static MobNilClass nilDef = new MobNilClass(null);
	private static MobNil nil = new MobNil(nilDef);
	
	public MobNilClass(MobObjectClass def) {
		super(def);
	}

	@Override
	public MobNil newInstance() {
		return MobNilClass.nil;
	}
}
