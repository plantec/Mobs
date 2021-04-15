package mob.model.primitives;

public class MobStringDef extends MobPrimitiveDef<String> {

	@Override
	public MobString newInstance(String mob) {
		return new MobString(this, mob);
	}

}
