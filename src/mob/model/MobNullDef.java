package mob.model;

public class MobNullDef extends MobEntityDef {

	@Override
	public MobEntity newInstance() {
		throw new Error("Should't be sent");
	}

}
