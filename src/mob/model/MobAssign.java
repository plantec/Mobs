package mob.model;

public class MobAssign extends MobUnit {
	public MobAssign() {
		super(new MobNullDef());
	}

	@Override
	public void accept(MobVisitor visitor) {
		visitor.visitAssign(this);
	}

}
