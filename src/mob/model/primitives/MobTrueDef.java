package mob.model.primitives;

public class MobTrueDef extends MobBoolDef {
	private static MobTrueDef trueDef = new MobTrueDef();
	private static MobTrue mtrue = new MobTrue(trueDef);

	@Override
	public MobTrue newInstance() {
		return MobTrueDef.mtrue;
	}

}
