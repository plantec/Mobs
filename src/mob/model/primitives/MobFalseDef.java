package mob.model.primitives;

public class MobFalseDef extends MobBoolDef {
	private static MobFalseDef falseDef = new MobFalseDef();
	private static MobFalse mfalse = new MobFalse(falseDef);
	
	@Override
	public MobFalse newInstance() {
		return MobFalseDef.mfalse;
	}

}
