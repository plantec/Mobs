package mob.model;

public class MobClass extends MobUnit {

	private String name;

	public MobClass(MobEntityDef def) {
		super(def);
	}

	public MobClass(MobEntityDef def, String name) {
		this(def);
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
}
