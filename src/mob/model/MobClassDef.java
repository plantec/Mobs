package mob.model;

public class MobClassDef extends MobEntityDef {
	
	public MobClass newInstance(String name) {
		return new MobClass(this, name);
	}

	@Override
	public MobClass newInstance() {
		return new MobClass(this);
	}

}
