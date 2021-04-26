package mob.model.primitives;

import mob.model.MobObjectDef;

public class MobNilDef extends MobObjectDef {
	private static MobNilDef nilDef = new MobNilDef();
	private static MobNil nil = new MobNil(nilDef);
	
	@Override
	public MobNil newInstance() {
		return MobNilDef.nil;
	}

}
