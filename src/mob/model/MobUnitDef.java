package mob.model;

public class MobUnitDef extends MobEntityDef {

	@Override
	public MobUnit newInstance() {
		return new MobUnit(this);
	}

}
