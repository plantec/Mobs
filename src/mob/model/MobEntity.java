package mob.model;

public abstract class MobEntity extends MobProtoEntity  {
	private MobEntityDef def;
	
	public MobEntity(MobEntityDef def) {
		this.def = def;
	}
		
	public MobEntityDef def() {
		return this.def;
	}
	
}
