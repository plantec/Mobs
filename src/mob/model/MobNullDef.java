package mob.model;

public class MobNullDef extends MobObjectDef {

	@Override
	public MobObject newInstance() {
		throw new Error("Should't be sent");
	}

}
