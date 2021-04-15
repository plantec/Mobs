package mob.model.primitives;

import mob.model.MobEntityDef;

public class MobNilDef extends MobEntityDef {
	private static MobNilDef nilDef = new MobNilDef();
	private static MobNil nil = new MobNil(nilDef);
	
	@Override
	public MobNil newInstance() {
		return MobNilDef.nil;
	}

}
