package mob.model;

public class MobUnit extends MobObject {
	private MobEntity contents;
	public MobUnit(MobObjectDef def) {
		super(def);
	}
	public MobUnit(MobObjectDef def, MobEntity contents) {
		super(def);
		this.setContents(contents);
	}
	public MobEntity contents() {
		return this.contents;
	}
	public void setContents(MobEntity contents) {
		this.contents = contents;
		contents.setParent(this);
	}

	@Override
	public void accept(MobVisitor visitor) {
		visitor.visitUnit(this);
		
	}
}
