package mob.model.primitives;

public class MobFloatDef extends MobPrimitiveDef<Float> {

	@Override
	public MobFloat newInstance(Float mob) {
		return new MobFloat(this, mob);
	}

}
